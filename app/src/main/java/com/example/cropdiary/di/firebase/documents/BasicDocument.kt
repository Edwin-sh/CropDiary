package com.example.cropdiary.di.firebase.documents

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class BasicDocument @Inject constructor(){
    fun emptyDocument(documentReference: DocumentReference): Task<Void> {
        return documentReference.set(mapOf<String, Any>())
    }
}