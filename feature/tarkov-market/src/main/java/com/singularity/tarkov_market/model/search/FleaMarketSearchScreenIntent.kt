package com.singularity.tarkov_market.model.search

internal sealed class FleaMarketSearchScreenIntent {
    data class SearchItem(val query: String) : FleaMarketSearchScreenIntent()
    data class ItemClicked(val id: String) : FleaMarketSearchScreenIntent()
    data object Retry : FleaMarketSearchScreenIntent()
    data object ClearQuery : FleaMarketSearchScreenIntent()
    data class ToggleFavourite(val id: String) : FleaMarketSearchScreenIntent()
}