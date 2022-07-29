package com.masmuzi.finalcoderstoryapps.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.masmuzi.finalcoderstoryapps.CoroutineRules
import com.masmuzi.finalcoderstoryapps.data.dummy.DataDummy
import com.masmuzi.finalcoderstoryapps.data.remote.response.LoginResponse
import com.masmuzi.finalcoderstoryapps.data.repo.UserRepo
import com.masmuzi.finalcoderstoryapps.data.result.Result
import com.masmuzi.finalcoderstoryapps.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepo
    private lateinit var loginVM: LoginVM
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyEmail = "user@email.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        loginVM = LoginVM(userRepository)
    }

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Test
    fun `when signup failed and Result Error`() {
        val loginResponse = MutableLiveData<Result<LoginResponse>>()
        loginResponse.value = Result.Error("Error")

        Mockito.`when`(loginVM.login(dummyEmail, dummyPassword)).thenReturn(loginResponse)

        val actualLoginResponse = loginVM.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Error)
    }

    @Test
    fun `when login success and Result Success`() {
        val expectedLoginResponse = MutableLiveData<Result<LoginResponse>>()
        expectedLoginResponse.value = Result.Success(dummyLoginResponse)

        Mockito.`when`(loginVM.login(dummyEmail, dummyPassword))
            .thenReturn(expectedLoginResponse)

        val actualLoginResponse = loginVM.login(dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(userRepository).login(dummyEmail, dummyPassword)
        Assert.assertNotNull(actualLoginResponse)
        Assert.assertTrue(actualLoginResponse is Result.Success)
        Assert.assertSame(dummyLoginResponse, (actualLoginResponse as Result.Success).data)
    }

    @Test
    fun `Save token successfully`() = mainCoroutineRule.runBlockingTest {
        loginVM.setToken(dummyToken, true)
        Mockito.verify(userRepository).setToken(dummyToken, true)
    }
}