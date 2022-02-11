package com.example.composeanimation

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun UpdateTransitionChild() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(currentState, label = "")

    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    Column {
        Canvas(
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .border(BorderStroke(1.dp, Color.Green))
        ) {
            drawPath(Path().apply { addRect(rect) }, Color.Red)
        }
        NumberPad(transition.createChildTransition { currentState })
        Button(onClick = {
            currentState =
                if (currentState == BoxState.Expanded) BoxState.Collapsed
                else BoxState.Expanded
        }) {
            Text("Click Me")
        }
    }

    LaunchedEffect(Unit) {
        currentState = BoxState.Expanded
    }
}

@Composable
fun NumberPad(transition: Transition<BoxState>) {
    val rect by transition.animateRect(transitionSpec = transitioningSpec(), label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
            BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
        }
    }

    Column {
        Canvas(
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .border(BorderStroke(1.dp, Color.Green))
        ) {
            drawPath(Path().apply { addRect(rect) }, Color.Red)
        }
    }
}