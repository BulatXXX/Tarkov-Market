package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.item.ItemDetailsIntent
import com.singularity.tarkov_market.model.item.ItemDetailsState
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.tarkov_market_data.type.LanguageCode
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ItemDetailsViewModel @AssistedInject constructor(
    val repository: FleaMarketRepository,
    @Assisted private val itemId: String,
) : MVIViewModel<ItemDetailsIntent, ItemDetailsState, Nothing>() {
    override fun setInitialState(): ItemDetailsState = ItemDetailsState()
    init {
        loadItem(itemId)
    }

    override fun handleIntent(intent: ItemDetailsIntent) {
        when (intent) {
            ItemDetailsIntent.ToggleFavourite -> {
                if(uiState.value.detailedItem?.isFavourite==false){
                    saveItem()
                }
                else{
                    deleteItem()
                }
            }
            ItemDetailsIntent.Refresh -> {}

            is ItemDetailsIntent.LoadItem -> {

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
