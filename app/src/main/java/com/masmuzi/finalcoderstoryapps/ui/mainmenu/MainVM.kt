package com.masmuzi.finalcoderstoryapps.ui.mainmenu

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.masmuzi.finalcoderstoryapps.data.local.entity.Story
import com.masmuzi.finalcoderstoryapps.data.repository.StoryRepo
import com.masmuzi.finalcoderstoryapps.data.repository.UserRepo
import kotlinx.coroutines.launch

class MainVM(private val userRepo: UserRepo, private val storyRepo: StoryRepo) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun isLogin() : LiveData<Boolean> {
        return userRepo.isLogin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

    fun getStories(token: String) : LiveData<PagingData<Story>> =
        storyRepo.getStories(token).cachedIn(viewModelScope)

    fun getName(): LiveData<String> {
        return userRepo.getUserName()
    }

}