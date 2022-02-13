package com.example.composeanimation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentSize() {

    val letter = 'X'
    var message by remember { mutableStateOf(letter.toString()) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .animateContentSize(
                    animationSpec = spring(0.5f, 500f),
                    finishedListener = { _, _ ->
                        message = newText(letter)
                    }
                )
        ) {
            Text(text = message)
        }

        Button(onClick = {
            message = newText(letter)
        }) {
            Text("Click Me")
        }
    }
}

private fun newText(letter: Char): String {
    val count = (0..30).random()
    var newText = ""
    repeat(count) {
        if (newText != "") newText += "\n"
        newText += letter
            .toString()
            .padEnd(count, letter)
    }
    return newText
}
