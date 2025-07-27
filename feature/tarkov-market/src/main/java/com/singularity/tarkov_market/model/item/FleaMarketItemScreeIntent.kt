package com.singularity.tarkov_market.model.item

internal sealed class FleaMarketItemScreenIntent {
    data object ToggleFavourite: FleaMarketItemScreenIntent()
    data object Refresh: FleaMarketItemScreenIntent()
    data class LoadItem(val id: String): FleaMarketItemScreenIntent()

}