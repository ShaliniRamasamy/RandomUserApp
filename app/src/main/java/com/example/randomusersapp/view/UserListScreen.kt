package com.example.randomusersapp.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.view.component.ItemCard
import com.example.randomusersapp.view.component.TopBar
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserList(navController: NavHostController, number: Int) {
    val viewModel: UserListViewModel = koinViewModel()
    val usersState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val isFirstLaunch = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(isFirstLaunch) {
        if(isFirstLaunch.value) {
            viewModel.getUsers(number)
            isFirstLaunch.value = false
        }
    }
    LaunchedEffect(usersState.errorMessage) {
        usersState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        if (usersState.isLoading) {
            Toast.makeText(context, "Please wait, loading data...", Toast.LENGTH_SHORT).show()
        } else {
            navController.popBackStack()
        }
    }

    when {
        usersState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        usersState.userList?.size != 0 -> {
            LoadUser(usersState.userList?: emptyList(), onItemClick = { item ->
                val encodedUser = Uri.encode(Gson().toJson(item))
                navController.navigate("details/$encodedUser")
            })
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No data available")
            }
        }
    }

}

@Composable
fun LoadUser(userList: List<Result>, onItemClick: (item:Result) -> Unit){
    LazyColumn {
        item {
            TopBar()
        }

        items(userList) { item ->
            ItemCard (
                item,
                onItemClicked = { it ->
                    onItemClick(it)
                }
            )
        }
    }
}
