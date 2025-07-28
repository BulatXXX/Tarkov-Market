package com.singularity.tarkov_market.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.singularity.tarkov_market.presenter.FavouritesViewModel
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class FavouritesViewModelFactory @Inject constructor(
    private val repository: FleaMarketRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        FavouritesViewModel(repository) as T
}