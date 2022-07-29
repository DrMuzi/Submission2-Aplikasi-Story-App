package com.masmuzi.finalcoderstoryapps.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.masmuzi.finalcoderstoryapps.data.remote.response.LoginResponse
import com.masmuzi.finalcoderstoryapps.data.remote.response.RegisterResponse
import com.masmuzi.finalcoderstoryapps.data.remote.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.lang.Exception
import com.masmuzi.finalcoderstoryapps.data.result.Result
import com.masmuzi.finalcoderstoryapps.ui.maps.MapStyle
import com.masmuzi.finalcoderstoryapps.ui.maps.MapType

class UserRepo private constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiService
){

    fun register(name: String, email: String, password: String) : LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.register(name, email, password)
            emit(Result.Success(result))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Result.Error(it.toString()))
                }
            } catch (exception: Exception) {
                emit(Result.Error(exception.message.toString()))
            }
        }
    }

    fun login(email: String, password: String) : LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val result = apiService.login(email, password)
            emit(Result.Success(result))
        }catch (throwable: HttpException){
            try {
                throwable.response()?.errorBody()?.source()?.let {
                    emit(Result.Error(it.toString()))
                }
            } catch (exception: Exception) {
                emit(Result.Error(exception.message.toString()))
            }
        }
    }

    fun isLogin() : Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[STATE_KEY] ?: false
        }
    }

    suspend fun setToken(token: String, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[STATE_KEY] = isLogin
        }
    }

    fun getToken() : Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }

    /**
     * User Email
     */

    fun getEmailUser(): Flow<String> = dataStore.data.map {
        it[USER_EMAIL_KEY] ?: ""
    }

    suspend fun saveEmailUser(email: String) {
        dataStore.edit {
            it[USER_EMAIL_KEY] = email
        }
    }

    fun getUserEmail() : LiveData<String> = getEmailUser().asLiveData()
    suspend fun saveUserEmail(value: String) = saveEmailUser(value)

    /**
     * User Name
     */

    fun getNmUser(): Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

    suspend fun saveNmUser(name: String) {
        dataStore.edit {
            it[USER_NAME_KEY] = name
        }
    }

    fun getUserName() : LiveData<String> = getNmUser().asLiveData()
    suspend fun saveUserName(value: String) = saveNmUser(value)

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN] = ""
            preferences[STATE_KEY] = false
        }
    }

    /**
     * Map Type
     */

    fun getType(): Flow<MapType> = dataStore.data.map {
        when (it[MAP_TYPE_KEY]) {
            MapType.NORMAL.name -> MapType.NORMAL
            MapType.SATELLITE.name -> MapType.SATELLITE
            MapType.TERRAIN.name -> MapType.TERRAIN
            else -> MapType.NORMAL
        }
    }

    suspend fun saveType(mapType: MapType) {
        dataStore.edit {
            it[MAP_TYPE_KEY] = when (mapType) {
                MapType.NORMAL -> MapType.NORMAL.name
                MapType.SATELLITE -> MapType.SATELLITE.name
                MapType.TERRAIN -> MapType.TERRAIN.name
            }
        }
    }

    fun getMapType() : LiveData<MapType> = getType().asLiveData()
    suspend fun saveMapType(value: MapType) =saveType(value)

    /**
     * Map Style
     */

    fun getStyle(): Flow<MapStyle> = dataStore.data.map {
        when (it[MAP_STYLE_KEY]) {
            MapStyle.NORMAL.name -> MapStyle.NORMAL
            MapStyle.NIGHT.name -> MapStyle.NIGHT
            MapStyle.SILVER.name -> MapStyle.SILVER
            else -> MapStyle.NORMAL
        }
    }

    suspend fun saveStyle(mapStyle: MapStyle) {
        dataStore.edit {
            it[MAP_STYLE_KEY] = when (mapStyle) {
                MapStyle.NORMAL -> MapStyle.NORMAL.name
                MapStyle.NIGHT -> MapStyle.NIGHT.name
                MapStyle.SILVER -> MapStyle.SILVER.name
            }
        }
    }

    fun getMapStyle() : LiveData<MapStyle> = getStyle().asLiveData()
    suspend fun saveMapStyle(value: MapStyle) = saveStyle(value)





    companion object {
        @Volatile
        private var INSTANCE: UserRepo? = null

        private val TOKEN = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val MAP_TYPE_KEY = stringPreferencesKey("map_type")
        private val MAP_STYLE_KEY = stringPreferencesKey("map_style")

        fun getInstance(
            dataStore: DataStore<Preferences>,
            apiService: ApiService
        ): UserRepo {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepo(dataStore, apiService)
                INSTANCE = instance
                instance
            }
        }


    }
}