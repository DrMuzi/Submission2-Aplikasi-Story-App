package com.masmuzi.finalcoderstoryapps.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.masmuzi.finalcoderstoryapps.data.StoryRemoteMedia
import com.masmuzi.finalcoderstoryapps.data.local.entity.Story
import com.masmuzi.finalcoderstoryapps.data.local.room.StoryDatabase
import com.masmuzi.finalcoderstoryapps.data.remote.response.StoryResponse
import com.masmuzi.finalcoderstoryapps.data.remote.response.UploadResponse
import com.masmuzi.finalcoderstoryapps.data.remote.network.ApiService
import com.masmuzi.finalcoderstoryapps.utils.wrapEspressoIdlingResource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception
import com.masmuzi.finalcoderstoryapps.data.result.Result


class StoryRepo (private val apiService: ApiService, private val storyDatabase: StoryDatabase){

    fun uploadStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?): LiveData<Result<UploadResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.uploadStory("Bearer $token",imageMultipart, desc, lat, lon)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryLocation(token: String) : LiveData<Result<StoryResponse>> = liveData{
        emit(Result.Loading)
        try {
            val client = apiService.getStories("Bearer $token", location = 1)
            emit(Result.Success(client))
        }catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStories(token: String): LiveData<PagingData<Story>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMedia(storyDatabase, apiService, token),
                pagingSourceFactory = {
                    storyDatabase.storyDao().getAllStories()
                }
            ).liveData
        }
    }
}