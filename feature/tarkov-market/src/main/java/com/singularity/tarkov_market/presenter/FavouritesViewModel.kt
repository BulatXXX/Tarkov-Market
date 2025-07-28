package com.singularity.tarkov_market.presenter

import androidx.lifecycle.viewModelScope
import com.singularity.core.MVIViewModel
import com.singularity.tarkov_market.model.favourites.FavouritesIntent
import com.singularity.tarkov_market.model.favourites.FavouritesState
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import com.singularity.tarkov_market_data.type.GameMode
import com.singularity.tarkov_market_data.type.LanguageCode
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FavouritesViewModel @Inject constructor(
    val fleaMarketRepository: FleaMarketRepository
) : MVIViewModel<FavouritesIntent, FavouritesState, Nothing>() {
    override fun setInitialState(): FavouritesState = FavouritesState()

    init {
        observeFavourites()
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            fleaMarketRepository.observeFavourites(LanguageCode.en, GameMode.pve).onStart {
                setState {
                    uiState.value.copy(isLoading = true)
                }
            }.catch { e ->
                setState { uiState.value.copy(isLoading = false, error = e.message) }
            }.collectLatest { items ->
                setState {
                    uiState.value.copy(
                        favouriteItems = items,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun handleIntent(intent: FavouritesIntent) = when (intent) {
        is FavouritesIntent.RemoveFavourite -> TODO()
    }
}