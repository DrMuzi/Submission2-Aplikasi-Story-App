package com.masmuzi.finalcoderstoryapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.masmuzi.finalcoderstoryapps.data.remote.network.ApiConfig
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInject {
    fun provideRepository(context: Context): UserRepo {
        val apiService = ApiConfig.getApiService()
        return UserRepo.getInstance(context.dataStore, apiService)
    }
}