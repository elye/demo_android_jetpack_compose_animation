package com.example.composeanimation

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSizeTransform() {
    val time = 500
    Column {
        var expanded by remember {
            mutableStateOf(false)
        }

        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                if (targetState) {
                    expandFading(time) using expandSizing(time)
                } else {
                    shrinkFading(time) using shrinkSizing(time)
                }

            }
        ) { targetExpanded ->
            Image(
                painter = painterResource(
                    id = if (targetExpanded)
                        R.drawable.img
                    else
                        R.drawable.ic_launcher_background
                ),
                contentDescription = "",
                modifier = Modifier.background(Color.Yellow)
            )
        }

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Hide" else "Show")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun shrinkSizing(time: Int) =
    SizeTransform { initialSize, targetSize ->
        keyframes {
            // Shrink to target height first
            IntSize(initialSize.width, targetSize.height) at time
            // Then shrink to target width
            durationMillis = time * 3
        }
    }

@OptIn(ExperimentalAnimationApi::class)
private fun shrinkFading(time: Int) =
    fadeIn(animationSpec = tween(time, time * 2)) with
            fadeOut(animationSpec = tween(time * 3))

@OptIn(ExperimentalAnimationApi::class)
private fun expandSizing(time: Int) =
    SizeTransform { initialSize, targetSize ->
        keyframes {
            // Expand to target width first
            IntSize(targetSize.width, initialSize.height) at time
            // Then expand to target height
            durationMillis = time * 3
        }
    }

@OptIn(ExperimentalAnimationApi::class)
private fun expandFading(time: Int) =
    fadeIn(animationSpec = tween(time * 3)) with
            fadeOut(animationSpec = tween(time))

