package com.messon.mess.app.presentation.ui.state
import com.messon.mess.app.domain.dataModel.MessTiming

sealed class MessTimingState {
    object Idle : MessTimingState()
    object Loading : MessTimingState()
    data class Success(val messTiming:List<MessTiming>) : MessTimingState()
    data class Error(val message: String) : MessTimingState()
}