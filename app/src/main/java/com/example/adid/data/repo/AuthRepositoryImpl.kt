package com.example.adid.data.repo

import com.example.adid.domain.repo.AuthRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override var currentUser: FirebaseUser? = Firebase.auth.currentUser

    override fun hasUser(): Boolean = Firebase.auth.currentUser != null

    override suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit,
        onError: (e: Exception) -> Unit,
    ) {
        withContext(Dispatchers.IO) {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete(true)
                } else {
                    it.exception?.let { exception -> onError(exception) }
                }
            }
        }
    }

    override suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                onComplete(it.isSuccessful)
            }
        }
    }

    override suspend fun logout() {
        Firebase.auth.signOut()
    }

}