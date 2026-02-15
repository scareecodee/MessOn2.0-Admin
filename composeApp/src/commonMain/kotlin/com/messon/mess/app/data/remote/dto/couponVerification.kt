package com.messon.mess.app.data.remote.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetailDto(

    @SerialName("coupon_id")
    val couponId: String,

    @SerialName("user_id")
    val uid: String,

    @SerialName("date")
    val date: LocalDate,

    @SerialName("meal")
    val mealSlot: String,

    @SerialName("used_at")
    val usedAt: Int?,

    @SerialName("expires_at")
    val expireAt: Long,

    @SerialName("signature")
    val signature:String

)


@Serializable
data class CouponVerificationResDto(
    @SerialName("valid")
    val isValid: Boolean
)

@Serializable
data class MarkCouponAsUsedResDto(
    @SerialName("markedAsTrue")
    val isMarkedCouponASUsed: Boolean
)