package com.masmuzi.finalcoderstoryapps.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo
import kotlinx.coroutines.launch

class LoginVM(private val repo: UserRepo) : ViewModel() {

    fun setToken(token: String, isLogin: Boolean){
        viewModelScope.launch {
            repo.setToken(token, isLogin)
        }
    }

    fun getToken() : LiveData<String> {
        return repo.getToken().asLiveData()
    }


    fun login(email: String, password: String) = repo.login(email, password)
}