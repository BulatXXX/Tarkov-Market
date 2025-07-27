package com.singularity.tarkov_market.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.singularity.tarkov_market.presenter.FleaMarketItemScreenViewModel
import com.singularity.tarkov_market_data.repository.FleaMarketRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class FleaMarketItemViewModelFactory @Inject constructor(
    private val repository: FleaMarketRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        FleaMarketItemScreenViewModel(repository) as T
}