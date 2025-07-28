package com.singularity.tarkov_market.model.search

internal sealed class SearchIntent {
    data class SearchItem(val query: String) : SearchIntent()
    data class ItemClicked(val id: String) : SearchIntent()
    data object Retry : SearchIntent()
    data object ClearQuery : SearchIntent()
    data class ToggleFavourite(val id: String) : SearchIntent()
}