package com.messon.mess.app.utils

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")
    object NotificationScreen: Screen(route ="notification_screen")
    object ComplaintScreen: Screen(route ="complaint_screen")
    object CollegeListScreen: Screen(route ="college_list_screen")

}