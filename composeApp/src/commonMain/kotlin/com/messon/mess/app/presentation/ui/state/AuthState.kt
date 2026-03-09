package com.messon.mess.app.presentation.ui.state

import com.messon.mess.app.domain.dataModel.User


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Unauthenticated: AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}