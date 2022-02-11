package com.example.composeanimation

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatableOnly() {
    Column {
        var enabled by remember { mutableStateOf(true) }
        val color = remember { Animatable(Color.Gray) }
        LaunchedEffect(enabled) {
            color.animateTo(
                if (enabled) Color.Green else Color.Red,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            )
        }
        Box(
            Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color.value))
        Button(onClick = { enabled = !enabled }) {
            Text("Click Me")
        }
    }
}