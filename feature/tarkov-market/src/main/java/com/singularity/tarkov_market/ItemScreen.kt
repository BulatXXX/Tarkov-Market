package com.singularity.tarkov_market

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ItemScreen(name: String){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(name)
    }
}