package com.example.composeanimation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun <T> animationSpec() = tween<T>(
    durationMillis = 3000,
    easing = LinearOutSlowInEasing
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSimple() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var currentPage by remember { mutableStateOf(0) }

        Box {
            Crossfade(
                targetState = currentPage,
                animationSpec = animationSpec()
            ) { screen -> ColorBoxOnly(screen) }

            AnimatedContent(targetState = currentPage,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideIn()
                    } else {
                        slideOut()
                    }.using(SizeTransform(clip = false))
                }
            ) { screen ->
                // Make sure to use `screen`, not `currentPage`.
                ColorBoxTextOnly(screen)
            }
        }
        Button(onClick = {
            currentPage = (0..0xFFFFFF).random()
        }) {
            Text("Click Me")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun slideOut() =
    slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth },
        animationSpec = animationSpec()
    ) + fadeIn(
        animationSpec = animationSpec()
    ) with slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = animationSpec()
    ) + fadeOut(animationSpec = animationSpec())

@OptIn(ExperimentalAnimationApi::class)
private fun slideIn() =
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = animationSpec()
    ) + fadeIn(
        animationSpec = animationSpec()
    ) with slideOutVertically(
        targetOffsetY = { fullHeight -> -fullHeight },
        animationSpec = animationSpec()
    ) + fadeOut(animationSpec = animationSpec())

@Composable
fun ColorBoxOnly(screen: Int) {
    Box(
        Modifier
            .size(100.dp)
            .background(Color(screen + 0xFF000000))
    )
}

@Composable
fun ColorBoxTextOnly(screen: Int) {
    Box(
        Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(get6DigitHex(screen), color = contrastColor(screen))
    }
}