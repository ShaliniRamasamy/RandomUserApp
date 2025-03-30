package com.example.randomusersapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UsersState(
    val isLoading: Boolean = false,
    val userList : List<Result> ?= null,
    val errorMessage: String? = null
)

class UserListViewModel(private val repository: UserRepository): ViewModel() {

    private val _uiState = MutableStateFlow(UsersState())
    val uiState: StateFlow<UsersState> = _uiState.asStateFlow()

    fun getUsers(number: Int) = viewModelScope.launch {

        _uiState.value = UsersState(isLoading = true)

        try{
            val result = repository.getUserList(number)
            _uiState.value = UsersState(isLoading = false)
            _uiState.value = UsersState(userList = result.body()?.results)
        }
        catch(e: Exception){
            _uiState.value = UsersState(errorMessage = "Failed to load users")
        }
    }
}