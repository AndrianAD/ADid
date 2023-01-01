package com.example.adid.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.adid.ui.HomeViewModel

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    onNavToLoginPage: () -> Unit,
) {

    val context = LocalContext.current
    val homeState = homeViewModel.homeState
    val isError = homeState.homeError != null

    if (isError) {
        Toast.makeText(context, homeState.homeError, Toast.LENGTH_SHORT).show()
    }

    if (homeState.logout) {
        onNavToLoginPage.invoke()
    }

    Column() {
        Text("This is your home screen  -${homeState.userName} ")

        Button(onClick = {
            homeViewModel.logOut()
        }) {
            Text(text = "LogOut")
        }
    }

}

