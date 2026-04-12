package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.ScanStat

sealed class ScanStatState {
    object Idle : ScanStatState()
    object Loading :  ScanStatState()
    data class Success(val scanStat:List<ScanStat>) :ScanStatState()
    data class Error(val message: String) :ScanStatState()
}