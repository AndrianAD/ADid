package com.example.adid.di

import com.example.adid.data.repo.AuthRepositoryImpl
import com.example.adid.data.repo.UserRepositoryImpl
import com.example.adid.domain.repo.AuthRepository
import com.example.adid.domain.repo.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

}