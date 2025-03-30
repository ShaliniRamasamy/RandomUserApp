package com.example.randomusersapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.view.InputScreen
import com.example.randomusersapp.view.UserDetail
import com.example.randomusersapp.view.UserList
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigations() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User App") },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { InputScreen(navController) }
            composable("users/{number}",
                arguments = listOf(navArgument("number") { type = NavType.IntType })) { backStackEntry ->
                val number = backStackEntry.arguments?.getInt("number")
                UserList(navController, number?: 1)
            }
            composable("details/{user}") { backStackEntry ->
                val userJson = backStackEntry.arguments?.getString("user")
                val user = Gson().fromJson(userJson, Result::class.java)
                if (user != null) {
                    UserDetail(navController, user = user)
                }
            }
        }
    }
}