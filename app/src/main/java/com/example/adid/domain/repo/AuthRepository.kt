package com.example.adid.domain.repo

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    var currentUser: FirebaseUser?

    fun hasUser(): Boolean

    suspend fun createUser(email: String, password: String, onComplete: (Boolean) -> Unit, onError: (e: Exception) -> Unit)

    suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit)

    suspend fun logout()

}