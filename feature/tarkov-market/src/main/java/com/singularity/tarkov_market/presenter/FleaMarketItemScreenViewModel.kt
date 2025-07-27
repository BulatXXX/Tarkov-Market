package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.item.FleaMarketItemScreenIntent
import com.singularity.tarkov_market.model.item.FleaMarketItemScreenState
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FleaMarketItemScreenViewModel @Inject constructor(
    val repository: FleaMarketRepository
) : MVIViewModel<FleaMarketItemScreenIntent, FleaMarketItemScreenState, Nothing>() {
    override fun setInitialState(): FleaMarketItemScreenState = FleaMarketItemScreenState()

    override fun handleIntent(intent: FleaMarketItemScreenIntent) {
        when (intent) {
            FleaMarketItemScreenIntent.ToggleFavourite -> {
                if(uiState.value.detailedItem?.isFavourite==false){
                    saveItem()
                }
                else{
                    deleteItem()
                }
            }
            FleaMarketItemScreenIntent.Refresh -> {}
            is FleaMarketItemScreenIntent.LoadItem -> {
                loadItem(intent.id)
            }
        }
    }

    private fun deleteItem() {
        val item = uiState.value.detailedItem ?: return


        setState { uiState.value.copy(detailedItem = item.copy(isFavourite = false)) }

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.deleteItem(item.id)
                }
            }.onFailure { e ->
                setState { uiState.value.copy(detailedItem = item, error = e.message) }
            }
        }
    }

    private fun saveItem() {
        val item = uiState.value.detailedItem ?: return
        setState { uiState.value.copy(detailedItem = item.copy(isFavourite = true)) }

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.saveItem(item.copy(isFavourite = true), LanguageCode.en)
                }
            }.onFailure { e ->
                setState { uiState.value.copy(detailedItem = item, error = e.message) }
            }
        }
    }

    private fun loadItem(id: String) = viewModelScope.launch {
        repository.getItemById(id, LanguageCode.en)
            .onStart {
                setState {
                    uiState.value.copy(
                        isLoading = true
                    )
                }
            }.catch { e ->
                setState {
                    uiState.value.copy(
                        isLoading = false,
                        error = e.message,
                    )
                }
            }.collect { detailedItem ->
                setState {
                    uiState.value.copy(
                        isLoading = false,
                        error = null,
                        detailedItem = detailedItem,
                    )
                }
            }

    }
}
