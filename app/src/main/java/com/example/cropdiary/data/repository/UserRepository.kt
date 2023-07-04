package com.example.cropdiary.data.repository

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.core.documents.CropDataDocumentCore
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.di.firebase.collections.UserDataCollection
import com.example.cropdiary.di.firebase.collections.UsersCollection
import com.example.cropdiary.di.firebase.documents.BasicDocument
import com.example.cropdiary.di.firebase.documents.UserDataDocument
import com.example.cropdiary.di.firebase.documents.UserDocumentReference
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataCollection: UserDataCollection,
    private val userDataDocument: UserDataDocument,
    private val userDocumentReference: UserDocumentReference,
    private val basicDocument: BasicDocument,
    private val usersCollection: UsersCollection,
    private val tasksHelper: TasksHelper
) : IUserRepository {
    override suspend fun createUser(): Task<Void> {
        return basicDocument.emptyDocument(userDocumentReference.document)
    }
    override suspend fun addDataUser(userModel: UserModel): Task<Void> {
        return userDataCollection.getCollection().document(FirestoreCollectionsHelper.DATA_INFO_DOCUMENT_KEY).set(userModel)
    }
    override suspend fun getUser(email:String): Task<DocumentSnapshot> {
        return usersCollection.collection.document(email).get()
    }

    override suspend fun getDataUser(documentSnapshot: DocumentSnapshot): Task<DocumentSnapshot> {
        return userDataCollection.getCollection(documentSnapshot.reference).document(FirestoreCollectionsHelper.DATA_INFO_DOCUMENT_KEY).get()
    }

    override suspend fun getAppUser(): (Result<DocumentSnapshot?>){
        return tasksHelper.getDocumentResult(userDataDocument.document.get())
    }
}