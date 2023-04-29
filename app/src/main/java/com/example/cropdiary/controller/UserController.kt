package com.example.cropdiary.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.*
import androidx.appcompat.app.AlertDialog
import com.example.cropdiary.R
import com.example.cropdiary.model.User
import com.example.cropdiary.util.ProviderType
import com.example.cropdiary.util.utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class UserController(
    @NonNull val context: Context
) {
    val firebaseAuth: FirebaseAuth = Firebase.auth

    /*private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userRef =
        firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)
*/
    fun signUpUser(user: User, callback: (FirebaseUser?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(user.idEmail, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result?.user)
                } else {
                    authException(it.exception!!)
                    callback(null)
                }
            }
    }

    fun signInUser(user: User, callback: (FirebaseUser?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(user.idEmail, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(it.result?.user)
                } else {
                    authException(it.exception!!)
                    callback(null)
                }
            }
    }

    fun recoveryPassword(email: String, callback: (Boolean) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true)
            } else {
                authException(it.exception!!)
                callback(false)
            }
        }
    }

    private fun authException(exception: Exception) {
        try {
            throw exception!!
        } catch (e: FirebaseAuthException) {
            val errorCode = e.errorCode
            if (errorCode == "ERROR_USER_NOT_FOUND") {
                utilities.showErrorAlert(
                    context,
                    context.getString(R.string.there_is_no_user_with_this_email)
                )
            } else if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                utilities.showErrorAlert(context, context.getString(R.string.email_already_in_use))
            } else if (errorCode == "ERROR_WRONG_PASSWORD") {
                utilities.showErrorAlert(context, context.getString(R.string.wrong_password))
            } else {
                Log.w("Tag", errorCode)
            }
        } catch (e: Exception) {
            utilities.showErrorAlert(
                context,
                context.getString(R.string.an_error_ocurred_authenticating_the_user)
            )
        }
    }

    fun createUser() {
        /*userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                userRef.set(hashMapOf<String, String>()).addOnSuccessListener {
                    Log.d("User Controller", "Se creó el documento")
                }.addOnFailureListener { e ->
                    Log.e("User Controller", "Ocurrió un error al crear el documento", e)
                }
            } else {
                Log.w("User Controller", "Ya existía el documento")
            }
        }*/
    }

    fun registreUser(user: User, activity: Activity, callback: (Boolean) -> Unit) {
        /*userRef.set(
            hashMapOf<String, String>(
                "photoProfile" to user.photoProfile,
                "name" to user.name,
                "lastname" to user.lastname,
                "phoneNumber" to user.phoneNumber,
                "idCardNumber" to user.idCardNumber
            )
        ).addOnSuccessListener {
            AlertDialog.Builder(activity).setMessage("Se creó correctamente el usuario")
                .setPositiveButton("OK", null).create().show()
            callback(true)
        }.addOnFailureListener { e ->
            AlertDialog.Builder(activity)
                .setMessage("Ocurrió un error al crear el usuario${e.message}")
                .setPositiveButton("OK", null)
                .create().show()
            callback(false)
        }*/
    }

    fun getUser(callback: (User?) -> Unit) {
        /*userRef.get().addOnSuccessListener { document ->
            if (document.data != null && document.data!!.isNotEmpty()) {
                val user = User(
                    document.id,
                    "",
                    document["photoProfile"].toString(),
                    document["name"].toString(),
                    document["lastname"].toString(),
                    document["phoneNumber"].toString(),
                    document["identificationNumber"].toString()
                )
                callback(user)
            } else {
                Log.w("User Controller", "No tiene datos")
                callback(null)
            }
        }.addOnFailureListener {
            Log.e("User Controller", "Error al obtener User")
            callback(null)
        }*/
    }

    fun signOut() {
        /*firebaseAuth!!.signOut()
        GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        activity.startActivity(Intent(activity, SingInActivity::class.java))*/
    }
}