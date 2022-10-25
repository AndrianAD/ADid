package com.example.adid

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adid.ui.Home
import com.example.adid.ui.LoginScreen
import com.example.adid.ui.LoginViewModel
import com.example.adid.ui.SignUpScreen

const val LOGIN = "LOGIN"
const val SIGN_UP = "SIGN_UP"
const val HOME = "HOME"
const val DETAILS = "DETAILS"

@Composable
fun Navigation(

    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,

    ) {

    NavHost(navController = navController, startDestination = LOGIN) {
        composable(route = LOGIN) {
            LoginScreen(onNavToSignUpPage = {
                navController.navigate(SIGN_UP) {
                    launchSingleTop = true
                    popUpTo(route = LOGIN) {
                        inclusive = true
                    }
                }
            }, onNavToHomePage = {
                navController.navigate(HOME) {
                    launchSingleTop = true
                    popUpTo(route = LOGIN) {
                        inclusive = true
                    }
                }
            }, loginViewModel = loginViewModel)
        }
        composable(route = SIGN_UP) {
            SignUpScreen(onNavToLogInPage = {
                navController.navigate(LOGIN) {
                    launchSingleTop = true
                    popUpTo(route = SIGN_UP) {
                        inclusive = true
                    }
                }
            }, onNavToHomePage = {
                navController.navigate(HOME) {
                    launchSingleTop = true
                    popUpTo(route = SIGN_UP) {
                        inclusive = true
                    }
                }
            }, loginViewModel = loginViewModel)
        }
        composable(route = HOME) {
            Home()
        }
    }
}