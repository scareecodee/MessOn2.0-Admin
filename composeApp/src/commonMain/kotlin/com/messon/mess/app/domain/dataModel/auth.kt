package com.messon.mess.app.domain.dataModel


data class  LoginResponse(
    val accessToken: String,
    val tokenType: String = "bearer",
    val user: User,
    val expiresIn: String? = null,
)