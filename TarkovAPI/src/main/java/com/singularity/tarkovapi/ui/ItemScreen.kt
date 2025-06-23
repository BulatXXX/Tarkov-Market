package com.singularity.tarkovapi.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ItemScreen(name: String){
    Text(
        text = "Item $name",
//        color = MaterialTheme.colorScheme.primary
    )
}