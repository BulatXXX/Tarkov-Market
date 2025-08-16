package com.singularity.tarkov_market_data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.models.toSearchedItem
import com.singularity.tarkov_market_data.remote.services.TarkovMarketService
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode

class SearchPagingSource(
    private val tarkovMarketService: TarkovMarketService,
    private val query: String,
    private val languageCode: LanguageCode,
    private val mode: GameMode,
    private val pageSize: Int
) : PagingSource<Int, SearchedItem>() {
    override fun getRefreshKey(state: PagingState<Int, SearchedItem>): Int? = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchedItem> =
        try {
            val offset = params.key ?: 0
            val data =
                tarkovMarketService.searchByName(query, languageCode, mode, params.loadSize, offset)
                    .filterNotNull()
                    .map { dto -> dto.toSearchedItem() }
            Log.d("Paging", "▶ load(offset=$offset, size=${params.loadSize})")
            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (data.isEmpty()) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


}