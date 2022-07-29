package com.masmuzi.finalcoderstoryapps.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.masmuzi.finalcoderstoryapps.data.dummy.DataDummy
import com.masmuzi.finalcoderstoryapps.data.result.Result
import com.masmuzi.finalcoderstoryapps.data.repo.StoriesRepo
import com.masmuzi.finalcoderstoryapps.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsVMTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storiesRepository: StoriesRepo
    private lateinit var mapsVM: MapsVM
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyStory = DataDummy.generateDummyStoryResponse()

    @Before
    fun setUp() {
        mapsVM = MapsVM(storiesRepository)
    }

    @Test
    fun `when Network error Should Return Error`() {
        val expectedStories = MutableLiveData<Result<StoriesResponse>>()
        expectedStories.value = Result.Error("Error")
        Mockito.`when`(mapsVM.getStories(dummyToken)).thenReturn(expectedStories)

        val actualStories = mapsVM.getStories(dummyToken).getOrAwaitValue()

        Mockito.verify(storiesRepository).getStoryLocation(dummyToken)
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Error)
    }

    @Test
    fun `when Get maps story Should Not Null and Return Success`() {
        val expectedStories = MutableLiveData<Result<StoriesResponse>>()
        expectedStories.value = Result.Success(dummyStory)
        Mockito.`when`(mapsVM.getStories(dummyToken)).thenReturn(expectedStories)

        val actualStories = mapsVM.getStories(dummyToken).getOrAwaitValue()

        Mockito.verify(storiesRepository).getStoryLocation(dummyToken)
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Success<*>)
        Assert.assertSame(dummyStory, (actualStories as Result.Success<*>).data)
        Assert.assertEquals(dummyStory.storiesList.size, actualStories.data.storiesList.size)
    }
}