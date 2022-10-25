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
fun SignUpScreen(
    loginViewModel: LoginViewModel,
    onNavToLogInPage: () -> Unit,
    onNavToHomePage: () -> Unit,
) {
    val loginState = loginViewModel.loginState
    val isError = loginState.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SignUp",
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
            value = loginState.userNameSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = loginViewModel::onUserNameSignUpChanged,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, null)
            },
            label = {
                Text(text = "Email")
            },
            isError = isError
        )


        OutlinedTextField(
            value = loginState.passwordSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = loginViewModel::onPasswordSignUpChanged,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, null)
            },
            label = {
                Text(text = "Password")
            },
            isError = isError
        )

        OutlinedTextField(
            value = loginState.confirmPasswordSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = loginViewModel::onConfirmPasswordSignUpChanged,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, null)
            },
            label = {
                Text(text = "Password Confirmation")
            },
            isError = isError
        )

        Button(onClick = {
            loginViewModel.createUser(context)
        }) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Already have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToLogInPage.invoke() }) {
                Text(text = "Sign In")
            }
        }

        if (loginState.isLoading) {
            CircularProgressIndicator()
        }

    }

}

