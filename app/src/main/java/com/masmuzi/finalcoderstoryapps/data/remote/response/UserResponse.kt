package com.masmuzi.finalcoderstoryapps.data.remote.response

import com.google.gson.annotations.SerializedName
import com.masmuzi.finalcoderstoryapps.data.model.StoryModel
import com.masmuzi.finalcoderstoryapps.data.model.UserModel

data class UserResponse(

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("loginResult")
	val loginResult: UserModel? = null,

    @field:SerializedName("listStory")
	val listStory: ArrayList<StoryModel>? = null
)
