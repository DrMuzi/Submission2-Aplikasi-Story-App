package com.masmuzi.finalcoderstoryapps.di

import android.content.Context
import com.masmuzi.finalcoderstoryapps.data.local.room.StoryDatabase
import com.masmuzi.finalcoderstoryapps.data.remote.network.ApiConfig
import com.masmuzi.finalcoderstoryapps.data.repository.StoryRepo

object StoryInject {
    fun provideRepository(context: Context): StoryRepo {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return StoryRepo(apiService, database)
    }
}