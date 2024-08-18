package com.wcsm.confectionaryadmin.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.model.entities.User

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun saveUserFirestore(user: User): Task<Void> {
        return firestore
            .collection("users")
            .document(user.id)
            .set(user)
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun deleteUserFirebaseAuth(user: FirebaseUser): Task<Unit> {
        val userDocRef = firestore.collection("users").document(user.uid)

        return firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userDocRef)

            if(snapshot.exists()) {
                transaction.delete(userDocRef)
            }
        }
    }

    override suspend fun deleteUserFirestore(user: FirebaseUser): Task<Void> {
        return user.delete()
    }
}