package com.messon.mess.app.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("id_token")
    val idToken: String
)

@Serializable
data class LoginResponseDto(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("token_type")
    val tokenType: String = "bearer",

    @SerialName("user")
    val user: UserDto,

    @SerialName("expires_at")
    val expiresIn: String? = null,
)

@Serializable

data class UserDto(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("name")
    val name: String? = null,

    @SerialName("picture")
    val picture: String? = null
)