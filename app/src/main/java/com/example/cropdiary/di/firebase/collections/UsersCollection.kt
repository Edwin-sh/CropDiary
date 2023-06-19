package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UsersCollection @Inject constructor(firestore: FirebaseFirestore) {
    val collection= firestore.collection(FirestoreCollectionsHelper.USERS_COLLECTION)
}