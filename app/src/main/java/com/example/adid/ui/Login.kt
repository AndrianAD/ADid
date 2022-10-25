package com.example.adid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavToSignUpPage: () -> Unit,
    onNavToHomePage: () -> Unit,
) {
    val loginState = loginViewModel.loginState
    val isError = loginState.loginError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
        )
        if (isError) {
            Text(
                text = loginState.loginError ?: "Unknown Error",
                color = MaterialTheme.colorScheme.error,
            )
        }


        OutlinedTextField(
            value = loginState.userNameLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = loginViewModel::onUserNameLoginChanged,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, null)
            },
            label = {
                Text(text = "Email")
            },
            isError = isError
        )


        OutlinedTextField(
            value = loginState.passwordLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = loginViewModel::onPasswordLoginChanged,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, null)
            },
            label = {
                Text(text = "Password")
            },
            isError = isError
        )

        Button(onClick = {
            loginViewModel.loginUser(context)
        }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Don't have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(
                onClick = { onNavToSignUpPage.invoke() }) {
                Text(text = "Sign Up")
            }
        }

        if (loginState.isLoading) {
            CircularProgressIndicator()
        }

    }

}

