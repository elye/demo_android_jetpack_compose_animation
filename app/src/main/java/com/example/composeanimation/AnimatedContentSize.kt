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
fun AnimatedContentSize() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var currentPage by remember { mutableStateOf(0) }
        var message by remember { mutableStateOf("") }
        var sizePage by remember { mutableStateOf(100.dp) }
//        Crossfade(
//            targetState = currentPage,
//            animationSpec = tween(durationMillis = 1000)
//        ) { currentPage ->
        ColorBoxSizeable(currentPage, sizePage, message)
//        }
        Button(onClick = {
            currentPage = (0..0xFFFFFF).random()
            sizePage = (100..200).random().dp
            message = newText('O')
        }) {
            Text("Click Me")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColorBoxSizeable(screen: Int, size: Dp, message: String) {
//    Box (Modifier
//        .animateContentSize(),
//        contentAlignment = Alignment.Center){
//        Box(
//            Modifier
//                .background(Color(screen + 0xFF000000))
//                .size(size),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(get6DigitHex(screen), color = contrastColor(screen))
//        }
//    }

    Box (Modifier
        .animateContentSize(),
        contentAlignment = Alignment.Center){
        Text(message)
    }

//    AnimatedContent(targetState = message,
//            transitionSpec = {
//                fadeIn(tween(5000)) with fadeOut(tween(5000))
//            }
//    ) {
//        message ->
//        Text(message)
//    }
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
