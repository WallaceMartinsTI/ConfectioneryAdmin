package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.wcsm.confectionaryadmin.data.model.entities.OrderWithCustomer
import com.wcsm.confectionaryadmin.data.model.entities.User

interface UserRepository {

    fun getCurrentUser(): FirebaseUser?

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    suspend fun saveUserFirestore(user: User): Task<Void>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun deleteUserFirebaseAuth(user: FirebaseUser): Task<Unit>

    suspend fun deleteUserFirestore(user: FirebaseUser): Task<Void>

}