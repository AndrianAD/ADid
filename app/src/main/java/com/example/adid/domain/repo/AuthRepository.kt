package com.example.adid.domain.repo

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthRepository {
    var currentUser: FirebaseUser?

    fun hasUser(): Boolean
    fun getUserId(): String

    suspend fun createUser(email: String, password: String, onComplete: (Boolean) -> Unit)

    suspend fun login(email: String, password: String, onComplete: (Boolean) -> Unit)

}