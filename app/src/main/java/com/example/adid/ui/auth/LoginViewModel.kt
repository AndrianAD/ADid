package com.example.adid.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adid.data.repo.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    fun onUserNameLoginChanged(userNameLogin: String) {
        loginState = loginState.copy(userNameLogin = userNameLogin)
    }

    fun onPasswordLoginChanged(passwordLogin: String) {
        loginState = loginState.copy(passwordLogin = passwordLogin)
    }

    fun onUserNameSignUpChanged(userNameSignUp: String) {
        loginState = loginState.copy(userNameSignUp = userNameSignUp)
    }

    fun onPasswordSignUpChanged(passwordSignUp: String) {
        loginState = loginState.copy(passwordSignUp = passwordSignUp)
    }

    fun onConfirmPasswordSignUpChanged(confirmPasswordSignUp: String) {
        loginState = loginState.copy(confirmPasswordSignUp = confirmPasswordSignUp)
    }

    fun validateLoginForm() = loginState.userNameLogin.isNotBlank() && loginState.passwordLogin.isNotBlank()

    private fun validateSignUpForm() = loginState.userNameSignUp.isNotBlank() && loginState.passwordSignUp.isNotBlank()

    fun hasUser(): Boolean {
        return authRepository.hasUser()
    }

    fun createUser() {
        loginState = loginState.copy(signUpError = null)
        viewModelScope.launch {
            try {
                if (!validateSignUpForm()) {
                    throw IllegalArgumentException("Email and password can not be empty")
                }
                if (loginState.passwordSignUp != loginState.confirmPasswordSignUp) {
                    throw IllegalArgumentException("Confirm Password does not match")
                }
                loginState = loginState.copy(isLoading = true)

                authRepository.createUser(loginState.userNameSignUp, loginState.passwordSignUp, onComplete = { isSuccessful ->
                    loginState = if (isSuccessful) {
                        loginState.copy(isSuccessLogin = true)
                    } else {
                        loginState.copy(isSuccessLogin = false)
                    }
                }, onError = { exception ->
                    loginState = loginState.copy(signUpError = exception.localizedMessage)
                })
            } catch (e: Exception) {
                loginState = loginState.copy(signUpError = e.localizedMessage)
            } finally {
                loginState = loginState.copy(isLoading = false)
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            try {
                if (!validateLoginForm()) {
                    throw IllegalArgumentException("Email and password can not be empty")
                }
                loginState = loginState.copy(isLoading = true)
                loginState = loginState.copy(loginError = null)
                authRepository.login(loginState.userNameLogin, loginState.passwordLogin) { isSuccessful ->
                    loginState = if (isSuccessful) {
                        loginState.copy(isSuccessLogin = true)
                    } else {
                        loginState.copy(isSuccessLogin = false)
                    }
                }
            } catch (e: Exception) {
                loginState = loginState.copy(loginError = e.localizedMessage)
            } finally {
                loginState = loginState.copy(isLoading = false)
            }
        }
    }

}

data class LoginState(
    var userNameLogin: String = "",
    var passwordLogin: String = "",
    var userNameSignUp: String = "",
    var passwordSignUp: String = "",
    var confirmPasswordSignUp: String = "",
    var isLoading: Boolean = false,
    var isSuccessLogin: Boolean = false,
    var loginError: String? = null,
    var signUpError: String? = null,
)