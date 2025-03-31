package com.example.randomusersapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.view.InputScreen
import com.example.randomusersapp.view.UserDetail
import com.example.randomusersapp.view.UserList
import com.example.randomusersapp.view.component.AppTopBar
import com.google.gson.Gson

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: "Home"

    Scaffold(
        topBar = {
            AppTopBar(
                title = currentDestination,
                onBackClick = if (navController.previousBackStackEntry != null) {
                    { navController.popBackStack() }
                } else null
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
                    UserDetail(user = user)
                }
            }
        }
    }
}