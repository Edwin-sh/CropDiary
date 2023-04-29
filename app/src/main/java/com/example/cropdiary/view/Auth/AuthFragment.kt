package com.example.cropdiary.view.Auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cropdiary.view.main.*
import com.example.cropdiary.R
import com.example.cropdiary.controller.UserController
import com.example.cropdiary.databinding.FragmentSignInBinding
import com.example.cropdiary.model.User
import com.example.cropdiary.util.FragmentsConstants
import com.example.cropdiary.util.ProviderType
import com.example.cropdiary.util.utilities

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSignInBinding.inflate(layoutInflater)
        userController = UserController(requireContext())
        //Setup
        setup()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setup() {
        with(binding) {
            btnSignUp.setOnClickListener { signUp() }
            btnSignIn.setOnClickListener { signIn() }
            tvForgotPassword.setOnClickListener { showForgotPassword() }
        }
    }

    private fun signUp() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireContext())) {
                return@with
            }
            if (!utilities.isValid(
                    editTextEmailAddress,
                    binding.editTextPassword,
                    requireContext()
                )
            ) {
                return@with
            }
            if (!utilities.networkConnection(requireContext())) {
                return@with
            }

            val userAuthResult = userController.signUpUser(
                User(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            ){firebaseUser->
                if (firebaseUser != null) {
                    utilities.showSuccesAlert(requireContext(), "Se registró correctamente"){
                        showPantallaInicial(firebaseUser.email!!, ProviderType.BASIC)
                    }

                }
            }

        }
    }

    private fun signIn() {
        with(binding) {
            val list = mutableListOf<Pair<EditText, String>>()
            list.add(Pair(editTextEmailAddress, getString(R.string.you_must_enter_the_email)))
            list.add(Pair(editTextPassword, getString(R.string.you_must_enter_the_password)))

            if (!utilities.noEmpty(list, requireContext())) {
                return@with
            }

            if (!utilities.isValid(editTextEmailAddress, requireContext())) {
                return@with
            }

            if (!utilities.networkConnection(requireContext())) {
                return@with
            }
            userController.signInUser(
                User(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            ) {
                if (it != null) {
                    showPantallaInicial(it.email!!, ProviderType.BASIC)
                }
            }
        }
    }


    /*override fun onStart() {
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
    */
    private fun showPantallaInicial(email: String, provider: ProviderType) {

        requireActivity().startActivity(
            Intent(requireContext(), MainActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider.name)
            }
        )
        requireActivity().finish()
    }

    private fun showForgotPassword() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragContainerAuth, RecoveryPasswordFragment())
            .addToBackStack(FragmentsConstants.FRAGMENT_AUTH)
            .commit()
    }
}