package com.messon.mess.app.presentation.ui.state
import com.messon.mess.app.domain.dataModel.MessRule

sealed class SaveRulesState {
    object Idle : SaveRulesState()
    object Loading : SaveRulesState()
    data class Success(val rules: List<MessRule>) : SaveRulesState()
    data class Error(val message: String) : SaveRulesState()
}

sealed class FetchRulesState {
    object Idle : FetchRulesState()
    object Loading :FetchRulesState()
    data class Success(val rules: List<MessRule>) : FetchRulesState()
    data class Error(val message: String) :FetchRulesState()
}

