package com.singularity.tarkov_market.model.favourites

internal sealed class FavouritesIntent {
    data class RemoveFavourite(val id: String) : FavouritesIntent()
}