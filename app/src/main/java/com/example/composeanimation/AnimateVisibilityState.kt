package com.example.composeanimation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateVisibilityState() {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column {
        AnimatedVisibility(
            visibleState = state,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            Text(text = "Hello, world!")
        }
        // Use the MutableTransitionState to know the current animation state
        // of the AnimatedVisibility.
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )
        Button(onClick = { state.targetState = !state.targetState }) {
            Text(if (state.targetState) "Hide" else "Show")
        }
    }
}