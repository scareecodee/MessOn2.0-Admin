package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.FetchPrefTime
import com.messon.mess.app.domain.dataModel.WindowTemplate

sealed class PrefWindowState {
    object Idle : PrefWindowState()
    object Loading : PrefWindowState()
    data class Success(val mealPrefTime: FetchPrefTime) : PrefWindowState()
    data class Error(val message: String) :PrefWindowState()
}

sealed class WindowTemplateState {
    object Idle : WindowTemplateState()
    object Loading : WindowTemplateState()
    data class Success(val template: WindowTemplate) : WindowTemplateState()
    data class Error(val message: String) :WindowTemplateState()
}