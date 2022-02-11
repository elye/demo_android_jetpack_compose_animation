package com.example.composeanimation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentSize() {
    val letter = 'X'
    var message by remember { mutableStateOf(letter.toString()) }
    Column {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .animateContentSize()
        ) {
            Text(text = message)
        }

        Button(onClick = {
            message = letter.toString().padEnd((0..30).random(), letter)
        }) {
            Text("Click Me")
        }
    }
}