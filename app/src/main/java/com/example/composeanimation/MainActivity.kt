package com.example.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeanimation.MainDestinations.ANIMATION_SCREEN
import com.example.composeanimation.MainDestinations.ANIMATION_TYPE
import com.example.composeanimation.MainDestinations.MAIN_SCREEN
import com.example.composeanimation.ui.theme.ComposeAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    NavGraph()
}

object MainDestinations {
    const val MAIN_SCREEN = "mainScreen"
    const val ANIMATION_SCREEN = "animationScreen"
    const val ANIMATION_TYPE = "animationType"
}

enum class ANIMATION_TYPE(val value: String, val func: @Composable () -> Unit) {
    ANIMATE_VISIBILITY("Animate Visibility", { AnimateVisibility() }),
    ANIMATE_VISIBILITY_STATE("Animate Visibility State", { AnimateVisibilityState() }),
    ANIMATE_VISIBILITY_NOT_GONE("Animate Visibility Not Gone", { AnimateVisibilityNotGone() }),
    CROSS_FADE("Cross Fade", { CrossFade() }),
    ANIMATE_CONTENT("Animate Content", { AnimatedContentSimple() }),
    ANIMATE_CONTENT_SIZE("Animate Content Size", { AnimatedContentSize() }),
    ANIMATE_CONTENT_SIZE_TRANSFORM("Animate Content Size Transform", { AnimatedContentSizeTransform() }),
    ANIMATE_CONTENT_ENTER_EXIT_CHILD("Animate Content Enter Exit Child", { AnimateEnterExitChild() }),
    ANIMATABLE_ONLY("Animatable", { AnimatableOnly() }),
    ANIMATE_AS_STATE("Animate As State", { AnimateAsState() }),
    UPDATE_TRANSITION_BASIC("Update Transition", { UpdateTransitionBasic() }),
    UPDATE_TRANSITION_CHILD("Update Transition Child", { UpdateTransitionChild() }),
    UPDATE_TRANSITION_EXTENSION("Update Transition Extension", { UpdateTransitionExtension() }),
    POINTER_INPUT("Pointer Input", { PointerInput() }),
    POINTER_SWIPE_TO_DISMISS("Pointer Swipe to Dismiss", { PointerSwipeToDismiss() })
}

@Composable
fun NavGraph(startDestination: String = MAIN_SCREEN) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MAIN_SCREEN) {
            MainScreen(actions)
        }
        composable(
            "$ANIMATION_SCREEN/{$ANIMATION_TYPE}",
            arguments = listOf(navArgument(ANIMATION_TYPE) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ChildScreen(arguments.getString(ANIMATION_TYPE))
        }
    }
}

class MainActions(navController: NavHostController) {
    val mainScreen: () -> Unit = {
        navController.navigate(MAIN_SCREEN)
    }
    val animationScreen: (String) -> Unit = { setting ->
        navController.navigate("$ANIMATION_SCREEN/$setting")
    }
}


@Composable
fun MainScreen(actions: MainActions) {
    @Composable
    fun ColumnScope.MyButton(
        title: ANIMATION_TYPE
    ) {
        Button(
            onClick = { actions.animationScreen(title.value) },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Text(title.value)
        }
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            enumValues<ANIMATION_TYPE>().forEach {
                MyButton(it)
            }
        }
    }
}

@Composable
fun ChildScreen(animationSetting: String?) {
    enumValues<ANIMATION_TYPE>().first { it.value == animationSetting }.func.invoke()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimationTheme {
        Greeting("Android")
    }
}