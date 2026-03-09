package com.messon.mess.app.utils

sealed class Screen(val route: String) {
    object MainScreen: Screen("main_screen")
    object LoginScreen: Screen("login_screen")
    object NotificationScreen: Screen(route ="notification_screen")
    object ComplaintScreen: Screen(route ="complaint_screen")
    object CollegeListScreen: Screen(route ="college_list_screen")
    object MenuCreateScreen: Screen(route ="menu_create_screen")
    object ViewMessMenuScreen: Screen(route ="view_mess_menu_screen")

    object MessRuleScreen: Screen(route ="mess_rule_screen")




}