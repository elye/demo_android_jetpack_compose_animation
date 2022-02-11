package com.example.composeanimation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun CrossFade() {
    Column {
        var currentPage by remember { mutableStateOf("0") }
        Crossfade(
            targetState = currentPage,
            animationSpec = tween(
                durationMillis = 3000,
                easing = LinearOutSlowInEasing
            )
        ) { screen ->
            Text("Page $screen")
        }
        Button(onClick = {
            currentPage = (0..10).random().toString()
        }) {
            Text("Click Me")
        }
    }
}

