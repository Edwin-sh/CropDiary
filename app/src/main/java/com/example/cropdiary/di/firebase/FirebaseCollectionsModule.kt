package com.example.cropdiary.di.firebase

import com.example.cropdiary.di.firebase.collections.UserDataCollection
import com.example.cropdiary.di.firebase.collections.UsersCollection
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object FirebaseCollectionsModule {
    @Provides
    @ActivityScoped
    fun provideUsersCollection(firestore: FirebaseFirestore): UsersCollection {
        return UsersCollection(firestore)
    }

    @Provides
    @ActivityScoped
    fun provideUsersDataCollection(usersCollection: UsersCollection): UserDataCollection {
        return UserDataCollection(usersCollection)
    }
}