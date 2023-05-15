package com.example.cropdiary.util

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseReferences {
    const val USERS_COLLECTION="users"
    val FIRESTORE= FirebaseFirestore.getInstance()

}