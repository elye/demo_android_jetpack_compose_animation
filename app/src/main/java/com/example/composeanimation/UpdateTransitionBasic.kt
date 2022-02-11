package com.example.composeanimation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

enum class BoxState {
    Collapsed,
    Expanded
}

@Composable
fun UpdateTransitionBasic() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(targetState = currentState, label = "")

    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    val color by transition.animateColor(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> MaterialTheme.colors.primary
            BoxState.Expanded -> MaterialTheme.colors.secondary
        }
    }

    val borderWidth by transition.animateDp(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> 5.dp
            BoxState.Expanded -> 20.dp
        }
    }

    Column {
        Canvas(modifier = Modifier.fillMaxWidth()
            .height(500.dp)
            .border(BorderStroke(borderWidth, Color.Green))) {
            drawPath(Path().apply { addRect(rect) }, color)
        }
        Button(onClick = {
            currentState = if(currentState == BoxState.Expanded)
                BoxState.Collapsed else BoxState.Expanded
        }) {
            Text("Click Me")
        }
    }
}

@Composable
fun <T>transitioningSpec(): @Composable() (Transition.Segment<BoxState>.() -> FiniteAnimationSpec<T>) =
    {
        when {
            BoxState.Expanded isTransitioningTo BoxState.Collapsed ->
                spring(stiffness =20f, dampingRatio = 0.25f)
            else ->
                tween(durationMillis = 3000)
        }
    }
