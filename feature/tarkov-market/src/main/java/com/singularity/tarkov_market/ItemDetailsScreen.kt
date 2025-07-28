package com.singularity.tarkov_market

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singularity.tarkov_market.di.DaggerFleaMarketComponent
import com.singularity.tarkov_market.di.FleaMarketDepsProvider
import com.singularity.tarkov_market.presenter.ItemDetailsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.singularity.tarkov_market.di.factories.ItemDetailsViewModelFactory
import com.singularity.tarkov_market.model.item.ItemDetailsIntent
import kotlinx.coroutines.Dispatchers

@Composable
fun ItemDetailsScreen(id: String) {
    val appComponent = remember {
        DaggerFleaMarketComponent.builder().deps(deps = FleaMarketDepsProvider.deps).build()
    }
    val assistedFactory = remember { appComponent.itemViewModelFactory }
    val factory = remember(id) {
        ItemDetailsViewModelFactory(assistedFactory, id)
    }
    val viewModel =
        viewModel<ItemDetailsViewModel>(factory = factory)

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Dispatchers.IO) {
        viewModel.sendIntent(ItemDetailsIntent.LoadItem(id))
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) CircularProgressIndicator()
        else {
            Column {
                AsyncImage(state.detailedItem?.image512PxLink, "")
                Text(state.detailedItem?.name.orEmpty())
                IconButton(onClick = {
                    viewModel.sendIntent(ItemDetailsIntent.ToggleFavourite)
                    Log.d("UI", state.detailedItem?.isFavourite.toString())
                }) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        imageVector =
                            if (state.detailedItem?.isFavourite == true) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            }, contentDescription = ""
                    )
                }
            }
        }
    }
}