package com.messon.mess.app.data.remote.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetailDto(

    @SerialName("coupon_id")
    val couponId: String,

    @SerialName("student_id")
    val uid: String,

    @SerialName("mess_id")
    val messId: String,

    @SerialName("college_id")
    val collegeId: String,

    @SerialName("date")
    val date: LocalDate,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("scanned_at")
    val usedAt: String?,

    @SerialName("signature")
    val signature:String,

    @SerialName("meal_type")
    val mealType: String

)

@Serializable
data class CouponVerificationResDto(
    @SerialName("student_name")
    val studentName: String,

    @SerialName("roll_no")
    val rollNo: String,

    @SerialName("branch")
    val branch: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("meal_type")
    val mealType: String,

    @SerialName("scanned_at")
    val scannedAt: String?,

    @SerialName("success")
    val isValid: Boolean,

    @SerialName("errors")
    val errors:List<ErrorDetailDto>
)

@Serializable
data class  ErrorDetailDto(
    @SerialName("code")
    val statusCode: Int,

    @SerialName("message")
    val message: String
)