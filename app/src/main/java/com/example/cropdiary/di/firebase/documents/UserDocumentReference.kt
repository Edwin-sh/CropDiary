package com.example.cropdiary.di.firebase.documents

import android.content.Context
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.di.firebase.collections.UsersCollection
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
class UserDocumentReference @Inject constructor(@ApplicationContext context: Context, usersCollection: UsersCollection) {
    val document: DocumentReference =usersCollection.collection.document(SharedPrefUserHelper.getUserPrefs(context).email.toString())
}