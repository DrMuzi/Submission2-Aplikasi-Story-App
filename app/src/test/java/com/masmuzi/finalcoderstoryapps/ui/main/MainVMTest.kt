package com.masmuzi.finalcoderstoryapps.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.masmuzi.finalcoderstoryapps.CoroutineRules
import com.masmuzi.finalcoderstoryapps.PaggingDataSourceTest
import com.masmuzi.finalcoderstoryapps.adapter.AdapterListStory
import com.masmuzi.finalcoderstoryapps.data.dummy.DataDummy
import com.masmuzi.finalcoderstoryapps.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainVMTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var mainVM: MainVM
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"

    @Test
    fun `set logout successfully`() = mainCoroutineRule.runBlockingTest {
        mainVM.logout()
        Mockito.verify(mainVM).logout()
    }

    @Test
    fun `get token successfully`() {
        val expectedToken = MutableLiveData<String>()
        expectedToken.value = dummyToken
        Mockito.`when`(mainVM.getToken()).thenReturn(expectedToken)

        val actualToken = mainVM.getToken().getOrAwaitValue()
        Mockito.verify(mainVM).getToken()
        Assert.assertNotNull(actualToken)
        Assert.assertEquals(dummyToken, actualToken)
    }

    @Test
    fun `when get list story should not sull`() = mainCoroutineRule.runBlockingTest {
        val dummyStories = DataDummy.generateDummyStoryList()
        val expectedStories = MutableLiveData<PagingData<Stories>>()
        expectedStories.value = PaggingDataSourceTest.snapshot(dummyStories)
        Mockito.`when`(mainVM.getStories(dummyToken)).thenReturn(expectedStories)
        val actualStories = mainVM.getStories(dummyToken).getOrAwaitValue()

        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
        val storyDiffer = AsyncPagingDataDiffer(
            diffCallback = AdapterListStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher,
        )
        storyDiffer.submitData(actualStories)

        advanceUntilIdle()

        Mockito.verify(mainVM).getStories(dummyToken)
        Assert.assertNotNull(actualStories)
        Assert.assertEquals(dummyStories.size, storyDiffer.snapshot().size)
    }

    @Test
    fun `get session login successfully`() {
        val dummySession = true
        val expectedSession = MutableLiveData<Boolean>()
        expectedSession.value = dummySession
        Mockito.`when`(mainVM.isLogin()).thenReturn(expectedSession)

        val actualSession = mainVM.isLogin().getOrAwaitValue()
        Mockito.verify(mainVM).isLogin()
        Assert.assertNotNull(actualSession)
        Assert.assertEquals(dummySession, actualSession)
    }
}