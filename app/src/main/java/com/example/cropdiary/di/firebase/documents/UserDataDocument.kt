package com.example.cropdiary.di.firebase.documents

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.di.firebase.collections.UserDataCollection
import javax.inject.Inject

class UserDataDocument @Inject constructor(private val userDataCollection: UserDataCollection) {
    val document=userDataCollection.getCollection().document(FirestoreCollectionsHelper.DATA_INFO_DOCUMENT_KEY)

}