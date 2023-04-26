package com.example.cropdiary.SignIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cropdiary.R

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioSesionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializacion Firebase auth, db y googleSIngInClient
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        //Validacion si esta loggeado
        if (auth.currentUser != null) {
            UserController(this).getUser(){user ->
                if (user!=null){
                    showPantallaInicial()
                }else{
                    showPantallaSignUp()
                }
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("27299456498-nvrsaah1d5gpu43c0vk096famff29hqm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInAppCompatButton.setOnClickListener() {
            val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = con.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                signIn()
            } else {
                Toast.makeText(this, "No estás conectado a internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("succes", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("error", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Valida si es nuevo usuario
                    val isNewUser = task.result?.additionalUserInfo?.isNewUser
                    val userController = UserController(this)
                    if (isNewUser == true) {
                        //Si es nuevo le crea la colección
                        userController.createUser()
                        showPantallaSignUp()
                    } else {
                        userController.getUser(){user ->
                            if (user != null) {
                                showPantallaInicial()
                            } else {
                                showPantallaSignUp()
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    private fun showPantallaInicial() {
        startActivity(
            Intent(this, MainActivity::class.java)
        )
        finish()
    }
}