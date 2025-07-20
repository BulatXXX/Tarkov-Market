package com.singularity.tarkov_market

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.singularity.tarkov_remote.models.DetailedItem
import com.singularity.tarkov_market.di.DaggerFleaMarketComponent
import com.singularity.tarkov_market.di.FleaMarketDepsProvider
import com.singularity.tarkov_market.model.FleaMarketSearchScreenIntent
import com.singularity.tarkov_market.presenter.FleaMarketSearchScreenViewModel
import com.singularity.tarkov_remote.models.SearchedItem


@Composable
fun SearchScreen(onItemClick: (name: String) -> Unit) {
    val appComponent =
        remember {
            DaggerFleaMarketComponent.builder().deps(deps = FleaMarketDepsProvider.deps).build()
        }
    val viewModel =
        viewModel<FleaMarketSearchScreenViewModel>(factory = appComponent.viewModelFactory)

    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button({ viewModel.sendIntent(FleaMarketSearchScreenIntent.SearchItem("AK")) }) {
            Text("Search")
        }

        LazyColumn {
            items(state.searchedItems, key = { it.id }) { ui ->
                ListItem(ui)
                HorizontalDivider(Modifier, 1.dp, Color.Gray)
            }
        }
    }
}

@Composable
fun ListItem(searchedItem: SearchedItem){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = searchedItem.iconLink,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(searchedItem.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${searchedItem.avg24hPrice} ₽",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}