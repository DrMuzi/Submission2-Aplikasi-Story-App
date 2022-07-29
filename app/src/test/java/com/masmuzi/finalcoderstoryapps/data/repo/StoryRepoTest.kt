package com.masmuzi.finalcoderstoryapps.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.masmuzi.finalcoderstoryapps.CoroutineRules
import com.masmuzi.finalcoderstoryapps.PaggingDataSourceTest
import com.masmuzi.finalcoderstoryapps.adapter.AdapterListStory
import com.masmuzi.finalcoderstoryapps.data.dummy.DataDummy
import com.masmuzi.finalcoderstoryapps.data.dummy.FakeApiService
import com.masmuzi.finalcoderstoryapps.data.remote.ApiRetrofits.ApiService
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
class StoriesReposTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineRules()

    @Mock
    private lateinit var storiesDatabase: StoriesDatabase
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var mockStoriesRepository: StoriesRepo
    private lateinit var storiesRepository: StoriesRepo
    private val dummyToken = "azhfxrdjgchfgchjvjhfhdgcvcnv"
    private val dummyFile = DataDummy.generateDummyMultipartFile()
    private val dummyRequestBody = DataDummy.generateDummyRequestBody()

    @Before
    fun setup() {
        apiService = FakeApiService()
        storiesRepository = StoriesRepo(apiService, storiesDatabase)
    }

    @Test
    fun `when stories location Should Not Null`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummyStoryResponse()
        val actualResponse = apiService.getStories(dummyToken, location = 1)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse.storiesList.size, actualResponse.storiesList.size)
    }

    @Test
    fun `Upload story successfully`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummyStoryUploadResponse()
        val actualResponse = apiService.uploadStory(dummyToken,dummyFile, dummyRequestBody, null, null)
        Assert.assertNotNull(expectedResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `Get stories paging - successfully`() = mainCoroutineRule.runBlockingTest {
        val dummyStories = DataDummy.generateDummyStoryList()

        val expectedStories = MutableLiveData<PagingData<Stories>>()
        expectedStories.value = PaggingDataSourceTest.snapshot(dummyStories)

        Mockito.`when`(mockStoriesRepository.getStories(dummyToken)).thenReturn(expectedStories)

        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
        val actualStories = mockStoriesRepository.getStories(dummyToken).getOrAwaitValue()
        val storyDiffer = AsyncPagingDataDiffer(
            diffCallback = AdapterListStory.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        storyDiffer.submitData(actualStories)
        Assert.assertNotNull(storyDiffer.snapshot())
        Assert.assertEquals(dummyStories.size, storyDiffer.snapshot().size)
    }
}

private fun Any.submitData(pagingData: PagingData<Stories>) {

}


