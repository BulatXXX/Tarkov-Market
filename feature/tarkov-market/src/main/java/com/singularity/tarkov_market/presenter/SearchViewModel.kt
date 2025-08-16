package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.search.SearchIntent
import com.singularity.tarkov_market.model.search.SearchState
import com.singularity.tarkov_market_data.models.SearchedItem
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SearchViewModel @Inject constructor(
    val fleaMarketRepository: FleaMarketRepository
) : MVIViewModel<SearchIntent, SearchState, Nothing>() {
    private var currentSearchJob: Job? = null

    override fun setInitialState() = SearchState()

    override fun handleIntent(intent: SearchIntent) =
        when (intent) {
            SearchIntent.ClearQuery -> setState { uiState.value.copy(query = "") }
            is SearchIntent.ItemClicked -> {}
            SearchIntent.Retry -> search(uiState.value.query)
            is SearchIntent.SearchItem -> setState { uiState.value.copy(query = intent.query) }
            is SearchIntent.ToggleFavourite -> {}
        }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingFlow: Flow<PagingData<SearchedItem>> =
        uiState
            .map { it.query }
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                fleaMarketRepository.searchPaged(q, LanguageCode.en, GameMode.pve)
            }


    private fun search(query: String) {

        currentSearchJob?.cancel()

        currentSearchJob = viewModelScope.launch {
            fleaMarketRepository
                .search(
                    query = query,
                    language = LanguageCode.en,
                    gameMode = GameMode.pve,
                    limit = 50,
                    offset = 0
                )
                .onStart {
                    setState {
                        uiState.value.copy(
                            query = query,
                            loading = true,
                            error = null,
                            searchedItems = emptyList()
                        )
                    }
                }
                .catch { e ->
                    setState {
                        uiState.value.copy(
                            loading = false,
                            error = e.message
                        )
                    }
                }
                .collectLatest { items ->
                    setState {
                        uiState.value.copy(
                            searchedItems = items,
                            loading = false,
                            error = null
                        )
                    }
                }
        }
    }

}

