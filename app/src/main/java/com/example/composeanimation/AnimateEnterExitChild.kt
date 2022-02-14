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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateEnterExitChild() {
    var visible by remember {
        mutableStateOf(true)
    }

    var color by remember {
        mutableStateOf(Color.Black)
    }
    Column( Modifier.fillMaxSize()) {
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Hide" else "Show")
        }
        Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(color))
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(
                durationMillis = 3000,
                easing = LinearOutSlowInEasing
            )
            ),
            exit = fadeOut(animationSpec = tween(
                durationMillis = 3000,
                easing = LinearOutSlowInEasing
            )
            )
        ) {
            val background by transition.animateColor(label = "") { state ->
                when(state) {
                    EnterExitState.PreEnter -> Color.Red
                    EnterExitState.PostExit -> Color.Green
                    EnterExitState.Visible -> Color.Blue
                }
            }

            color = background

            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(background)
            ) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            // Slide in/out the inner box.
                            enter = slideInVertically(
                                animationSpec = tween(
                                    durationMillis = 3000,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                            exit = slideOutVertically(
                                animationSpec = tween(
                                    durationMillis = 3000,
                                    easing = LinearOutSlowInEasing
                                )
                            )
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    // Content of the notification…
                }
            }
        }
    }
}
