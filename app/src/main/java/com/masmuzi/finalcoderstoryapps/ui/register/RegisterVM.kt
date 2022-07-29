package com.masmuzi.finalcoderstoryapps.ui.register

import androidx.lifecycle.ViewModel
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo

class RegisterVM(private val repo: UserRepo) : ViewModel() {

    fun register(name: String, email: String, password: String) = repo.register(name, email, password)
}