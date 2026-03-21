package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.FetchPrefTime

sealed class PrefWindowState {
    object Idle : PrefWindowState()
    object Loading : PrefWindowState()
    data class Success(val mealPrefTime: FetchPrefTime) : PrefWindowState()
    data class Error(val message: String) :PrefWindowState()
}