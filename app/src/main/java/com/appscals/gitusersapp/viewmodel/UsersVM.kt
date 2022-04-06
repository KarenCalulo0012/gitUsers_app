package com.appscals.gitusersapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appscals.gitusersapp.model.User
import com.appscals.gitusersapp.network.ApiInterface
import kotlinx.coroutines.launch

class UsersVM : ViewModel() {
    var userListResponse: List<User> by mutableStateOf(listOf())
    var errorMsg: String by mutableStateOf("")

    fun getUserList() {
        viewModelScope.launch {
            val apiService = ApiInterface.getInstance()
            try {
                val userList = apiService.getAllUsers()
                userListResponse = userList
            } catch (e: Exception) {
                errorMsg = e.message.toString()
            }
        }
    }
}