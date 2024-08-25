package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser

interface UserRepository {

    suspend fun getCurrentUser(): FirebaseUser?

    fun signOut()

    suspend fun getUserData(userId: String): Task<DocumentSnapshot>

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    suspend fun saveUserFirestore(firestoreUser: FirestoreUser): Task<Void>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun deleteUserAuth(user: FirebaseUser): Task<Unit>

    suspend fun deleteUserFirestore(user: FirebaseUser): Task<Void>

}