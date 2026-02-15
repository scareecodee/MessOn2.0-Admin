package com.messon.mess.app.utils

import com.messon.mess.app.domain.dataModel.CouponVerificationRes
import com.messon.mess.app.domain.dataModel.MarkCouponAsUsed

sealed class CouponVerificationState {
    object Idle : CouponVerificationState()
    object Loading : CouponVerificationState()
    data class Success(val couponVerificationRes: CouponVerificationRes) : CouponVerificationState()
    data class Error(val message: String) : CouponVerificationState()
}

sealed class MarkCouponAsUsedState {
    object Idle : MarkCouponAsUsedState()
    object Loading : MarkCouponAsUsedState()
    data class Success(val markCouponAsUsed: MarkCouponAsUsed) :MarkCouponAsUsedState()
    data class Error(val message: String) : MarkCouponAsUsedState()
}