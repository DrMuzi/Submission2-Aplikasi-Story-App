package com.masmuzi.finalcoderstoryapps.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masmuzi.finalcoderstoryapps.data.repository.Repo_Old
import com.masmuzi.finalcoderstoryapps.data.remote.response.UserResponse
import com.masmuzi.finalcoderstoryapps.data.model.StoryModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewStoryViewModel(private val userRepository: Repo_Old): ViewModel() {
    var myLocationPermission = MutableLiveData<Boolean>()

    fun setMyLocationPermission(value: Boolean) {
        myLocationPermission.value = value
    }

    fun getMapType() : LiveData<MapType> = userRepository.getMapType()

    fun saveMapType(mapType: MapType) {
        viewModelScope.launch {
            userRepository.saveMapType(mapType)
        }
    }

    fun getMapStyle() : LiveData<MapStyle> = userRepository.getMapStyle()

    fun saveMapStyle(mapStyle: MapStyle) {
        viewModelScope.launch {
            userRepository.saveMapStyle(mapStyle)
        }
    }

    private var _userStories = MutableLiveData<ArrayList<StoryModel>>()
    val userStories: LiveData<ArrayList<StoryModel>> = _userStories

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getUserToken() : LiveData<String> = userRepository.getUserToken()

    suspend fun getUserStoriesWithLocation(token: String) {
        val client = userRepository.getUserStoryMapView(token)

    }
}