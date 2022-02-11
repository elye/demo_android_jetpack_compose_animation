package com.example.composeanimation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSimple() {
    fun <T>animationSpec() = tween<T>(
        durationMillis = 3000,
        easing = LinearOutSlowInEasing
    )
    Row {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        Button(onClick = { count-- }) {
            Text("Minus")
        }
        AnimatedContent(targetState = count,
            transitionSpec = {
                // Compare the incoming number with the previous number.
                if (targetState > initialState) {
                    // If the target number is larger, it slides up and fades in
                    // while the initial (smaller) number slides up and fades out.
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight },
                        animationSpec = animationSpec()) +
                            fadeIn(animationSpec = animationSpec()) with
                            slideOutVertically(
                                targetOffsetY = { fullHeight -> -fullHeight },
                                animationSpec = animationSpec()) +
                            fadeOut(animationSpec = animationSpec())
                } else {
                    // If the target number is smaller, it slides down and fades in
                    // while the initial number slides down and fades out.
                    slideInVertically(
                        initialOffsetY = { fullHeight -> -fullHeight },
                        animationSpec = animationSpec()) +
                            fadeIn(animationSpec = animationSpec()) with
                            slideOutVertically(
                                targetOffsetY = { fullHeight -> fullHeight },
                                animationSpec = animationSpec()) +
                            fadeOut(animationSpec = animationSpec())
                }.using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            // Make sure to use `targetCount`, not `count`.
            Text(text = "$targetCount")
        }
    }
}