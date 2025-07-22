package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.FleaMarketSearchScreenIntent
import com.singularity.tarkov_market.model.FleaMarketSearchScreenState
import com.singularity.tarkov_market_data.remote.models.SearchedItem
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FleaMarketSearchScreenViewModel @Inject constructor(val fleaMarketRepository: FleaMarketRepository) :
    MVIViewModel<FleaMarketSearchScreenIntent, FleaMarketSearchScreenState, Nothing>() {
    private var currentSearchJob: Job? = null

    override fun setInitialState() = FleaMarketSearchScreenState()

    override fun handleIntent(intent: FleaMarketSearchScreenIntent) =
        when (intent) {
            FleaMarketSearchScreenIntent.ClearQuery -> setState { uiState.value.copy(query = "") }
            is FleaMarketSearchScreenIntent.ItemClicked -> {}
            FleaMarketSearchScreenIntent.Retry -> search(uiState.value.query)
            is FleaMarketSearchScreenIntent.SearchItem -> search(query = intent.query)
            is FleaMarketSearchScreenIntent.ToggleFavourite -> {}
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

