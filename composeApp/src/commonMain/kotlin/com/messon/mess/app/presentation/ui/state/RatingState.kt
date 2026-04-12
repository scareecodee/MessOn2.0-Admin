package com.messon.mess.app.presentation.ui.state


import com.messon.mess.app.domain.dataModel.RatingData

sealed class RatingState{
    object Idle : RatingState()
    object Loading : RatingState()
    data class Success(val ratings:List<RatingData>) : RatingState()
    data class Error(val message: String) : RatingState()
}