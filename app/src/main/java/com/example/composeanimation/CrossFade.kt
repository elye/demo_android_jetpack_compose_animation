package com.example.composeanimation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils

@Composable
fun CrossFade() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var currentPage by remember { mutableStateOf(0) }
        Crossfade(
            targetState = currentPage,
            animationSpec = tween(durationMillis = 1000)
        ) { screen -> ColorBox(screen)
        }
        Button(onClick = {
            currentPage = (0..0xFFFFFF).random()
        }) {
            Text("Click Me")
        }
    }
}

@Composable
fun ColorBox(screen: Int) {
    Box(
        Modifier
            .size(100.dp)
            .background(Color(screen + 0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        Text(get6DigitHex(screen), color = contrastColor(screen))
    }
}

fun get6DigitHex(value: Int): String {
    return "0x" + "%x".format(value).padStart(6, '0').toUpperCase(Locale.current)
}

fun contrastColor(color: Int): Color {
    return if (ColorUtils.calculateLuminance(color) < 0.5)
        Color.White
    else
        Color.Black
}
