package com.example.adid.data.repo

import com.example.adid.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    override fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

}
