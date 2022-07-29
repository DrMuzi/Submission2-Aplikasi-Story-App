package com.masmuzi.finalcoderstoryapps.data.result

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @field:SerializedName("token")
    val token: String
)
