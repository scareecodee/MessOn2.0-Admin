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

    object MealPrefWindow: Screen(route ="meal_pref_window")

    object MessTimingScreen: Screen(route ="mess_timing_screen")

    object MessTimeSetting: Screen(route ="mess_time_setting")

    object CouponDetailScreen: Screen(route ="coupon_detail_screen")

    object PrefStatScreen: Screen(route ="pref_stat_screen")

    object WindowTemplateScreen: Screen(route ="window_template_screen")

    object ScanAnalyticsScreen: Screen(route ="scan_analytics_screen")

    object MealRatingScreen: Screen(route ="meal_rating_screen")






}