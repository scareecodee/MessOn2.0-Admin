package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.CouponDetailDto
import com.messon.mess.app.data.remote.dto.CouponVerificationResDto
import com.messon.mess.app.data.remote.dto.MarkCouponAsUsedResDto
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.domain.dataModel.CouponVerificationRes
import com.messon.mess.app.domain.dataModel.MarkCouponAsUsed
import kotlinx.datetime.LocalDate


fun CouponDetailDto.toDomain(): CouponDetail{
    return CouponDetail(
        uid = uid,
        date = date,
        mealSlot = mealSlot,
        usedAt = usedAt,
        expiredAt = expireAt,
        couponId = couponId,
        signature = signature,
    )
}


fun CouponDetail.toDto(): CouponDetailDto{

    return CouponDetailDto(
        uid = uid?:"",
        couponId=couponId?:"",
        date = date?: LocalDate(1970, 1, 1),
        mealSlot =mealSlot?:"",
        usedAt =usedAt,
        expireAt =expiredAt?:0,
        signature=signature?:"",
    )
}


fun CouponVerificationResDto.toDomain(): CouponVerificationRes{
    return CouponVerificationRes(
        isValid = isValid
    )
}


fun MarkCouponAsUsedResDto.toDomain(): MarkCouponAsUsed{
    return MarkCouponAsUsed(
        isMarkedCouponAsUsed = isMarkedCouponASUsed
    )
}


