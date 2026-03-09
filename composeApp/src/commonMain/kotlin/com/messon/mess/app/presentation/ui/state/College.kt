package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.College

sealed class FetchCollegesState {
    object Idle : FetchCollegesState()
    object Loading : FetchCollegesState()
    data class Success(val colleges: List<College>) : FetchCollegesState()
    data class Error(val message: String) : FetchCollegesState()
}