package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import com.singularity.tarkov_remote.repository.FleaMarketRepository
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.FleaMarketSearchScreenIntent
import com.singularity.tarkov_market.model.FleaMarketSearchScreenState
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FleaMarketSearchScreenViewModel @Inject constructor(val fleaMarketRepository: FleaMarketRepository) :
    MVIViewModel<FleaMarketSearchScreenIntent, FleaMarketSearchScreenState, Nothing>() {
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
        viewModelScope.launch {
            setState { uiState.value.copy(query = query, loading = true, error = null, searchedItems = emptyList()) }
            runCatching {
                fleaMarketRepository.search(query)
            }.onSuccess { itemsList ->
                setState {
                    uiState.value.copy(
                        searchedItems = itemsList,
                        loading = false,
                        error = null,
                    )
                }
            }.onFailure {
                setState {
                    uiState.value.copy(
                        loading = false,
                        error = it.message,
                    )
                }
            }

        }
    }

}

