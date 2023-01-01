package com.example.adid.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adid.data.repo.AuthRepositoryImpl
import com.example.adid.data.repo.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        homeState=homeState.copy(userName = userRepository.getUser()?.email.orEmpty())
    }

    fun logOut() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                homeState = homeState.copy(logout = true)
            } catch (e: Exception) {

            } finally {

            }
        }
    }
}

data class HomeState(
    var userName: String = "",
    var homeError: String? = null,
    var logout: Boolean = false,
)