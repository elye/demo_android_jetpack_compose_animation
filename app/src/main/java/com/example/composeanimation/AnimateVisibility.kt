package com.example.composeanimation

import android.animation.TimeInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.math.sin

class CircularSpringInterpolator(private val tension: Float = 50f)
    : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return (sin(tension * input) * sin(Math.PI * input)
                + input).toFloat()
    }
}
internal fun CircularSpringInterpolatorEasing(tension: Float = 50f)
        : Easing = CircularSpringInterpolator(tension).toEasing()

fun TimeInterpolator.toEasing() = Easing { x -> getInterpolation(x) }

private val HesitateEasing = CubicBezierEasing(0f, 1f, 1f, 0f)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateVisibility() {
    var visible by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(1000)) + expandVertically (
                animationSpec = tween(1500,
                    easing = BounceInterpolator().toEasing())),
            exit = fadeOut(tween(1000)) + shrinkVertically (
                animationSpec = tween(1500,
                    easing = BounceInterpolator().toEasing()))
        ) {
            Text(text = "Hello, world!")
        }
        Button(onClick = { visible = !visible }) {
            Text("Click Me")
        }
    }
}
