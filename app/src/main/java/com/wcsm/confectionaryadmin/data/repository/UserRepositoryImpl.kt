package com.wcsm.confectionaryadmin.data.repository

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun saveUserFirestore(firestoreUser: FirestoreUser): Task<Void> {
        return firestore
            .collection("users")
            .document(firestoreUser.id)
            .set(firestoreUser)
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun getUserData(userId: String): Task<DocumentSnapshot> {
        return firestore
            .collection("users")
            .document(userId)
            .get()
    }

    override suspend fun deleteUserAuth(user: FirebaseUser): Task<Unit> {
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