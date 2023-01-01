package com.example.adid.domain.repo

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun getUser(): FirebaseUser?

}