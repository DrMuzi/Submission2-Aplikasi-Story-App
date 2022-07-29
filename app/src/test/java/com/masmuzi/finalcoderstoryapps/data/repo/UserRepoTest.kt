package com.masmuzi.finalcoderstoryapps.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.masmuzi.finalcoderstoryapps.CoroutineRules
import com.masmuzi.finalcoderstoryapps.data.dummy.DataDummy
import com.masmuzi.finalcoderstoryapps.data.dummy.FakeApiService
import com.masmuzi.finalcoderstoryapps.data.remote.ApiRetrofits.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var dataStore : DataStore<Preferences>
    private lateinit var userRepository: UserRepo

    private val dummyName = "User"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        apiService = FakeApiService()
        userRepository = UserRepo.getInstance(dataStore, apiService)
    }

    @Test
    fun `when login response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DataDummy.generateDummyLoginResponse()
        val actualResponse = apiService.login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }

    @Test
    fun `when register response Should Not Null`() = mainCoroutineRule.runBlockingTest{
        val expectedResponse = DataDummy.generateDummyRegisterResponse()
        val actualResponse = apiService.register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(actualResponse, expectedResponse)
    }
}