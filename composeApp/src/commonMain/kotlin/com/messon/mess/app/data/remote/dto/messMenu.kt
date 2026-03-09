package com.messon.mess.app.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMenuReqDto(
    @SerialName("day")
    val day: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("meal_type")
    val mealType: String,

    @SerialName("dishes")
    val dishes: String
)


@Serializable
data class MenuResDto(

    @SerialName("day")
    val day: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("meal_type")
    val mealType: String,

    @SerialName("dishes")
    val dishes: String
)