package com.example.cropdiary.data.repository

import android.app.Activity
import android.util.Log
import com.example.cropdiary.core.FirebaseHelper
import com.example.cropdiary.core.FirestoreCollections
import com.example.cropdiary.data.model.UserModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserRepository() : IUserRepository {

    private val database: FirebaseFirestore = FirebaseHelper.getFirebaseFirestore()
    private val usersCollection: CollectionReference =
        database.collection(FirestoreCollections.USERS_COLLECTION)

    /*private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userRef =
        firebaseFirestore.collection("users")
*/

    fun createUser() {
        /*userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                userRef.set(hashMapOf<String, String>()).addOnSuccessListener {
                    Log.d("UserModel Controller", "Se creó el documento")
                }.addOnFailureListener { e ->
                    Log.e("UserModel Controller", "Ocurrió un error al crear el documento", e)
                }
            } else {
                Log.w("UserModel Controller", "Ya existía el documento")
            }
        }*/
    }

    fun registreUser(userModel: UserModel, activity: Activity, callback: (Boolean) -> Unit) {
        /*userRef.set(
            hashMapOf<String, String>(
                "photoProfile" to userModel.photoProfile,
                "name" to userModel.name,
                "lastname" to userModel.lastname,
                "phoneNumber" to userModel.phoneNumber,
                "idCardNumber" to userModel.idCardNumber
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

    /*fun signOut(provider:ProviderType) {


        activity.startActivity(
            Intent(activity, AuthActivity::class.java)
        )
        activity.finish()
    }*/
    override suspend fun create(userUserModel: UserModel): UserModel {
        withContext(Dispatchers.IO) {

        }
        return UserModel("", "", "", "", "", "", "")
    }

    override suspend fun getUser(email: String): (Result<UserModel?>) {
        return withContext(Dispatchers.IO) {
            try {
                val result = usersCollection.document(email).get().await()
                if (result.exists()) {
                    val userModel = UserModel(
                        result.id,
                        "",
                        result["photoProfile"].toString(),
                        result["name"].toString(),
                        result["lastname"].toString(),
                        result["phoneNumber"].toString(),
                        result["identificationNumber"].toString()
                    )
                    Result.success(userModel)
                } else {
                    Log.w("UserModel Controller", "No tiene datos")
                    Result.success(null)
                }
            } catch (ex: Exception) {
                Log.e("UserModel Controller", "Error al obtener UserModel")
                Result.failure(ex)
            }
        }
    }
}