package com.masmuzi.finalcoderstoryapps.ui.story

import androidx.lifecycle.ViewModel
import com.masmuzi.finalcoderstoryapps.data.repository.StoryRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryVM(private val storyRepo: StoryRepo) : ViewModel() {

    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        desc: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ) = storyRepo.uploadStory(token, imageMultipart, desc, lat, lon)
}