package com.example.adid.ui.auth

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    var pressedTime: Long = 0
    val loginState = loginViewModel.loginState
    val isError = loginState.loginError != null
    val context = LocalContext.current

    BackHandler {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            (context as? Activity)?.finish()
        } else {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
        )
        if (isError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
            loginViewModel.loginUser()
        }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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

        LaunchedEffect(key1 = loginViewModel.hasUser(), block = {
            if (loginViewModel.hasUser()) {
                onNavToHomePage.invoke()
            }
        })

    }

}

