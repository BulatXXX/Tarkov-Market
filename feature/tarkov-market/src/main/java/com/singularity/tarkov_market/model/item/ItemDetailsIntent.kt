package com.singularity.tarkov_market.model.item

internal sealed class ItemDetailsIntent {
    data object ToggleFavourite: ItemDetailsIntent()
    data object Refresh: ItemDetailsIntent()
    data class LoadItem(val id: String): ItemDetailsIntent()

}