package com.messon.mess.app.presentation.ui.state
import com.messon.mess.app.domain.dataModel.Menu

sealed class SaveMenuState {
    object Idle :SaveMenuState()
    object Loading : SaveMenuState()
    data class Success(val menus: List<Menu>) : SaveMenuState()
    data class Error(val message: String) : SaveMenuState()
}
sealed class FetchMenuState {
    object Idle : FetchMenuState()
    object Loading :  FetchMenuState()
    data class Success(val menus: List<Menu>) : FetchMenuState()
    data class Error(val message: String) : FetchMenuState()
}