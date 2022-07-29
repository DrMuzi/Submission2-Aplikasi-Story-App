package com.masmuzi.finalcoderstoryapps.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masmuzi.finalcoderstoryapps.data.repository.StoryRepo
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo
import com.masmuzi.finalcoderstoryapps.di.StoryInject
import com.masmuzi.finalcoderstoryapps.di.UserInject
import com.masmuzi.finalcoderstoryapps.ui.mainmenu.MainVM
import com.masmuzi.finalcoderstoryapps.ui.maps.MapsVM
import com.masmuzi.finalcoderstoryapps.ui.story.StoryVM

class FactoryStoryVM private constructor(private val userRepo: UserRepo, private val storyRepo: StoryRepo) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainVM::class.java) -> {
                MainVM(userRepo, storyRepo) as T
            }
            modelClass.isAssignableFrom(StoryVM::class.java) -> {
                StoryVM(storyRepo) as T
            }
            modelClass.isAssignableFrom(MapsVM::class.java) -> {
                MapsVM(storyRepo) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryStoryVM? = null
        fun getInstance(context: Context): FactoryStoryVM =
            instance ?: synchronized(this) {
                instance ?: FactoryStoryVM(UserInject.provideRepository(context), StoryInject.provideRepository(context))
            }.also { instance = it }
    }
}