package com.example.composeanimation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Combination() {
    var enabled by remember { mutableStateOf(false) }

    val dbAnimateAsState: Dp by animateDpAsState(
        targetValue = switch(enabled),
        animationSpec = animationSpec()
    )

    val dbAnimatable = remember { Animatable(0.dp) }

    val transition = updateTransition(enabled, label = "")
    val dbTransition by transition.animateDp(
        transitionSpec = { animationSpec() }, label = "") {
        switch(it)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("AnimateAsState")
        animateBoxHorizontal(dbAnimateAsState)
        Text("Animatable")
        animateBoxHorizontal(dbAnimatable.value)
        Text("UpdateTransition")
        animateBoxHorizontal(dbTransition)

        Button(onClick = { enabled = !enabled }) {
            Text("Click Me")
        }
    }

    LaunchedEffect(key1 = enabled) {
        dbAnimatable.animateTo(
            targetValue = switch(enabled),
            animationSpec = animationSpec()
        )
    }
}


private fun animationSpec(): TweenSpec<Dp> =
    tween(
        durationMillis = 3000,
        easing = LinearOutSlowInEasing
    )

private fun switch(enabled: Boolean) = if (enabled) 268.dp else 0.dp

fun Animatable(initialValue: Dp) = Animatable(
    initialValue,
    DpToVector,
)

private val DpToVector: TwoWayConverter<Dp, AnimationVector1D> =
    TwoWayConverter({ AnimationVector1D(it.value) }, { it.value.dp })

@Composable
private fun animateBoxHorizontal(dbAnimateAsState: Dp) {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(300.dp)
            .background(Color.Yellow)
    ) {
        Box(
            modifier = Modifier
                .padding(dbAnimateAsState, 0.dp, 0.dp, 0.dp)
                .size(32.dp)
                .background(Color.Red)
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}