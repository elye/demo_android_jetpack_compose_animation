package com.example.composeanimation

import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentSizeTransform() {

    var expanded by remember {
        mutableStateOf(true)
    }

    Column {

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Hide" else "Show")
        }

        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(1500, 1500)) with
                        fadeOut(animationSpec = tween(1500)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(initialSize.width, targetSize.height) at 1500
                                    durationMillis = 3000
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(targetSize.width, initialSize.height) at 1500
                                    durationMillis = 3000
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Expanded()
            } else {
                Icon()
            }
        }
    }
}

@Composable
fun Expanded() {
    Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
}

@Composable
fun Icon() {
    Image(
        painterResource(R.drawable.ic_launcher_background),
        contentDescription = "description of the image")
}
