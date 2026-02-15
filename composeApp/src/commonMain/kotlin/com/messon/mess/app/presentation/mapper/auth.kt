package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.LoginResponseDto
import com.messon.mess.app.domain.dataModel.LoginResponse


fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        user = user.toDomain(),
        accessToken =accessToken,
        expiresIn = expiresIn,
        tokenType = tokenType
    )
}