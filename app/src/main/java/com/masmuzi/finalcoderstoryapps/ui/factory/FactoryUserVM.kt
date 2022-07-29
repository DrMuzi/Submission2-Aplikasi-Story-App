package com.masmuzi.finalcoderstoryapps.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo
import com.masmuzi.finalcoderstoryapps.di.UserInject
import com.masmuzi.finalcoderstoryapps.ui.login.LoginVM
import com.masmuzi.finalcoderstoryapps.ui.maps.MapsVM
import com.masmuzi.finalcoderstoryapps.ui.register.RegisterVM

class FactoryUserVM (private val userRepo: UserRepo) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterVM::class.java) -> {
                RegisterVM(userRepo) as T
            }
            modelClass.isAssignableFrom(LoginVM::class.java) -> {
                LoginVM(userRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryUserVM? = null
        fun getInstance(context: Context): FactoryUserVM =
            instance ?: synchronized(this) {
                instance ?: FactoryUserVM(UserInject.provideRepository(context))
            }.also { instance = it }
    }
}