package com.messon.mess.app.theme



import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val animationDuration = 350


val enterLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(
        animationSpec = tween(400)
    )
}


val exitRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(
        animationSpec = tween(animationDuration)
    )
}

val popEnterRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(
        animationSpec = tween(animationDuration)
    )
}

val popExitLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(
        animationSpec = tween(animationDuration)
    )
}