package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.UserDto
import com.messon.mess.app.domain.dataModel.User


fun UserDto.toDomain(): User {
    return User(
        name = name,
        mailId = email,
        profileImg = picture?:"",
        id = id,
    )
}

fun User.toDto(id:String): UserDto{
    return UserDto(
        name = name,
        email = mailId,
        picture = profileImg,
        id = id
    )
}