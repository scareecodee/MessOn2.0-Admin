package com.messon.mess.app.presentation.mapper
import com.messon.mess.app.data.remote.dto.CreateMenuReqDto
import com.messon.mess.app.data.remote.dto.MenuResDto
import com.messon.mess.app.domain.dataModel.Menu

fun Menu.toDto(): CreateMenuReqDto{
    return CreateMenuReqDto(
        day =day,
        mealSlot =mealSlot,
        mealType = mealType,
        dishes = dishes
    )
}

fun MenuResDto.toDomain():Menu{
    return Menu(
        day = day,
        mealSlot = mealSlot,
        mealType = mealType,
        dishes = dishes
    )
}