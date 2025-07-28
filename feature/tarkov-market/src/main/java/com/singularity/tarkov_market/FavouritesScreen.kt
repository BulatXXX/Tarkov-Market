package com.singularity.tarkov_market


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.singularity.tarkov_market.di.DaggerFleaMarketComponent
import com.singularity.tarkov_market.di.FleaMarketDepsProvider
import com.singularity.tarkov_market.presenter.FavouritesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.singularity.tarkov_market_data.models.FavouriteItem

@Composable
fun FavouritesScreen(onItemClick: (id: String) -> Unit) {
    val appComponent =
        remember {
            DaggerFleaMarketComponent.builder().deps(deps = FleaMarketDepsProvider.deps).build()
        }
    val viewModel =
        viewModel<FavouritesViewModel>(factory = appComponent.favouritesViewModelFactory)
    val state by viewModel.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn {
            items(state.favouriteItems, key = { it.id }) { favouriteItem ->
                ListItem(favouriteItem, modifier = Modifier.clickable(onClick = {onItemClick(favouriteItem.id)}))
                HorizontalDivider(Modifier, 1.dp, Color.Gray)
            }
        }
    }
}

@Composable
fun ListItem(favouriteItem: FavouriteItem,modifier: Modifier){
    Row(modifier = modifier) {
        AsyncImage(favouriteItem.iconLink,"")
        Text(favouriteItem.name)
    }
}