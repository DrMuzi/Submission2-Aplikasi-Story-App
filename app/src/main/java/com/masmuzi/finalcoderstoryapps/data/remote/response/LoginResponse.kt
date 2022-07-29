package com.masmuzi.finalcoderstoryapps.data.remote.response

import com.masmuzi.finalcoderstoryapps.data.result.LoginResult
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult")
    val loginResult: LoginResult? = null,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
