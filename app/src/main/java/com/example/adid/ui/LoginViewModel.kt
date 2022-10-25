package com.example.adid.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adid.domain.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

//    val state= combine(loginState){
//        loginState=loginState
//    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000))

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

    fun createUser(context: Context) {
        viewModelScope.launch {
            try {
                if (!validateSignUpForm()) {
                    throw IllegalArgumentException("Email and password can not be empty")
                }
                if (loginState.passwordSignUp != loginState.confirmPasswordSignUp) {
                    throw IllegalArgumentException("Confirm Password does not match")
                }
                loginState = loginState.copy(isLoading = true)
                loginState = loginState.copy(signUpError = null)
                authRepository.createUser(loginState.userNameSignUp, loginState.passwordSignUp) { isSuccessful ->
                    loginState = if (isSuccessful) {
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()
                        loginState.copy(isSuccessLogin = true)
                    } else {
                        Toast.makeText(context, "Failed SignUp", Toast.LENGTH_SHORT).show()
                        loginState.copy(isSuccessLogin = false)
                    }
                }
            } catch (e: Exception) {
                loginState = loginState.copy(signUpError = e.localizedMessage)
            } finally {
                loginState = loginState.copy(isLoading = false)
            }
        }
    }

    fun loginUser(context: Context) {
        viewModelScope.launch {
            try {
                if (!validateLoginForm()) {
                    throw IllegalArgumentException("Email and password can not be empty")
                }
                loginState = loginState.copy(isLoading = true)
                loginState = loginState.copy(loginError = null)
                authRepository.login(loginState.userNameLogin, loginState.passwordLogin) { isSuccessful ->
                    loginState = if (isSuccessful) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        loginState.copy(isSuccessLogin = true)
                    } else {
                        Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show()
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
    var loginError: String? = "",
    var signUpError: String? = "",
)