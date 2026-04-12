package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.EachDayPrefStat

sealed class PrefStatState {
    object Idle : PrefStatState()
    object Loading :  PrefStatState()
    data class Success(val prefStat: List<EachDayPrefStat>) :PrefStatState()
    data class Error(val message: String) :PrefStatState()
}