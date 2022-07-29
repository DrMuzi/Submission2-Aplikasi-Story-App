package com.masmuzi.finalcoderstoryapps.ui.maps

import androidx.lifecycle.ViewModel
import com.masmuzi.finalcoderstoryapps.data.repository.StoryRepo


class MapsVM(private val storyRepo: StoryRepo) : ViewModel() {
    fun getStories(token: String) = storyRepo.getStoryLocation(token)
}