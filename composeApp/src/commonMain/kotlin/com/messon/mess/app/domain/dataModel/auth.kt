package com.messon.mess.app.domain.dataModel

import com.messon.mess.app.utils.UserRole


data class  LoginResponse(
    val accessToken: String,
    val tokenType: String = "bearer",
    val user: User,
    val expiresIn: String? = null,
)

data class LoginRequest(
    val idToken: String,
    val role: String= UserRole.COLLEGE_ADMIN.value,
    val collegeId: String
)