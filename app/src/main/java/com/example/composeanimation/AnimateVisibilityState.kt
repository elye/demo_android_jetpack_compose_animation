package com.example.composeanimation

import android.view.animation.BounceInterpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateVisibilityState() {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(tween(1000)) + expandVertically (
                animationSpec = tween(1500,
                    easing = BounceInterpolator().toEasing())),
            exit = fadeOut(tween(1000)) + shrinkVertically (
                animationSpec = tween(1500,
                    easing = BounceInterpolator().toEasing()))
        ) {
            // Use the MutableTransitionState to know the current animation state
            // of the AnimatedVisibility.
            Text(text = when {
                state.isIdle && state.currentState -> "Hello, World!"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> ""
                else -> "Appearing"
            })
        }
        Button(onClick = { state.targetState = !state.targetState }) {
            Text("Click Me")
        }
    }
}