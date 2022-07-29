package com.masmuzi.finalcoderstoryapps

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState

class PaggingDataSourceTest private constructor() :
    PagingSource<Int, LiveData<List<Stories>>>() {
    companion object {
        fun snapshot(items: List<Stories>): PagingData<Stories> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Stories>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Stories>>> {
        return LoadResult.Page(emptyList(), 0 , 1)
    }
}