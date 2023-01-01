package com.example.adid

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adid.ui.*
import com.example.adid.ui.auth.LoginScreen
import com.example.adid.ui.auth.LoginViewModel
import com.example.adid.ui.auth.SignUpScreen
import com.example.adid.ui.home.Home

const val LOGIN = "LOGIN"
const val SIGN_UP = "SIGN_UP"
const val HOME = "HOME"
const val DETAILS = "DETAILS"

@Composable
fun Navigation(

    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,

    ) {

    NavHost(navController = navController, startDestination = LOGIN) {
        composable(route = LOGIN) {
            LoginScreen(onNavToSignUpPage = {
                navController.navigate(SIGN_UP) {
                    launchSingleTop = true
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
            Home(homeViewModel,
                onNavToLoginPage = {
                    navController.navigate(LOGIN) {
                        launchSingleTop = true
                        popUpTo(route = SIGN_UP) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}