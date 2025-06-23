package com.singularity.tarkovapi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier


@Composable
fun SearchScreen(onItemClick: (String) -> Unit) {
    Column {
        Text(text = "M4A1", modifier = Modifier.clickable { onItemClick("M4A1") })
        Text(text = "LedX", modifier = Modifier.clickable { onItemClick("LedX") })
        Text(text = "M4A3", modifier = Modifier.clickable { onItemClick("M4A3") })
    }
}
