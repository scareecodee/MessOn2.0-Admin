package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.CouponVerificationRes

sealed class CouponVerificationState {
    object Idle : CouponVerificationState()
    object Loading : CouponVerificationState()
    data class Success(val couponVerificationRes: CouponVerificationRes) : CouponVerificationState()
    data class Error(val message: String) : CouponVerificationState()
}

