package com.messon.mess.app.domain.dataModel


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetail(
    val uid: String?,
    val date: LocalDate?,
    val mealSlot: String?,
    val usedAt:Int?,
    val expiredAt: Long?,
    val couponId: String?,
    val signature: String?
) {
    fun isValid(): Boolean =
        !uid.isNullOrBlank() &&
                date.toString().isNotBlank() &&
                !mealSlot.isNullOrBlank() &&
                !couponId.isNullOrBlank() &&
                !signature.isNullOrBlank() &&
                expiredAt != null
}

data class CouponVerificationRes(
    val isValid: Boolean
)

data class MarkCouponAsUsed(
    val isMarkedCouponAsUsed: Boolean
)


