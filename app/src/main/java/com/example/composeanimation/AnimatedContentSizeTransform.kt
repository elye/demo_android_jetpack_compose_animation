package com.example.composeanimation

import androidx.compose.animation.*
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

    val time = 1500

    Column {
        var expanded by remember {
            mutableStateOf(false)
        }

        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(time, time)) with
                        fadeOut(animationSpec = tween(time)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand to target height first
                                    IntSize(initialSize.width, targetSize.height) at time
                                    // Then expand to target width
                                    durationMillis = time * 2
                                }
                            } else {
                                keyframes {
                                    // Shrink to target width first
                                    IntSize(targetSize.width, initialSize.height) at time
                                    // Then shrink to target height
                                    durationMillis = time * 2
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            Image(
                painter = painterResource(id = if (targetExpanded) R.drawable.img else R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.background(Color.Yellow)
            )
        }

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Hide" else "Show")
        }
    }
}

