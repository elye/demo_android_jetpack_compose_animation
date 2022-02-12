package com.example.composeanimation

import android.view.animation.BounceInterpolator
import androidx.compose.animation.*
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
fun AnimateVisibilityNotGone() {
    var visible by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(20.dp)) {
            this@Column.AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(1000)) + expandVertically(
                    animationSpec = tween(
                        1500,
                        easing = BounceInterpolator().toEasing()
                    )
                ),
                exit = fadeOut(tween(1000)) + shrinkVertically(
                    animationSpec = tween(
                        1500,
                        easing = BounceInterpolator().toEasing()
                    )
                )
            ) {
                Text(text = "Hello, world!")
            }
        }
        Button(onClick = { visible = !visible }) {
            Text("Click Me")
        }
    }
}
