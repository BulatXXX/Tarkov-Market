package com.singularity.tarkov_market.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.singularity.tarkov_market.presenter.ItemDetailsViewModel
import dagger.assisted.AssistedFactory

@Suppress("UNCHECKED_CAST")
internal class ItemDetailsViewModelFactory(
    private val assistedFactory: ItemDetailsViewModelAssistedFactory,
    private val itemId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
       assistedFactory.create(itemId) as T

}

@AssistedFactory
internal interface ItemDetailsViewModelAssistedFactory {
    fun create(itemId: String): ItemDetailsViewModel
}