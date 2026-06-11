package com.messon.mess.app.domain.dataModel
data class Menu(
    val day: String,
    val mealSlot: String,
    val mealType: String,
    val dishes: String?
)


data class MenuItem(
    val veg: String = "",
    val nonVeg: String = ""
)

data class DayMenu(
    val breakfast: MenuItem = MenuItem(),
    val lunch: MenuItem = MenuItem(),
    val dinner: MenuItem = MenuItem()
)


