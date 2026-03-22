package com.messon.mess.app.theme



import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val animationDuration = 450
val enterLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(animationDuration)
    ) + fadeIn(animationSpec = tween(animationDuration))
}

val exitRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(animationDuration)
    ) + fadeOut(animationSpec = tween(animationDuration))
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