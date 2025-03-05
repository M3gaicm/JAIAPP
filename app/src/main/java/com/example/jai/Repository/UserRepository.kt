package com.example.jai.Repository

import com.example.jai.navBar.ApiService
import com.example.jai.navBar.RetrofitClient
import com.example.jai.navBar.User

class UserRepository(private val apiService: ApiService = RetrofitClient.apiService) {

    suspend fun getUsers(): List<User> {
        return try {
            apiService.getUsers()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
