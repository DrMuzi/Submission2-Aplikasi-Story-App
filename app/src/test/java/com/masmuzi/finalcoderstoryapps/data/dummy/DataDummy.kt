package com.masmuzi.finalcoderstoryapps.data.dummy

import com.masmuzi.finalcoderstoryapps.data.remote.response.*
import com.masmuzi.finalcoderstoryapps.data.result.LoginResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyLoginResponse(): LoginResponse {
        val loginResult = LoginResult(
            token = "eyKlbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyQRQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flTYaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I"
        )

        return LoginResponse(
            loginResult = loginResult,
            error = false,
            message = "success"
        )
    }

    fun generateDummyStoryResponse(): StoriesResponse {
        val stories = arrayListOf<StoriesItemList>()

        for (i in 0 until 10) {
            val story = StoriesItemList(
                "story-FvU4u0Vp2S3PMsFg",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Dimas",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return StoriesResponse(stories, false, "Stories fetched successfully")
    }

    fun generateDummyStoryList(): List<Stories> {
        val stories = arrayListOf<Stories>()

        for (i in 0 until 5) {
            val story = Stories(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1641645658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return stories
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequestBody(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }

    fun generateDummyStoryUploadResponse(): UploadResponse {
        return UploadResponse(
            error = false,
            message = "success"
        )
    }
}