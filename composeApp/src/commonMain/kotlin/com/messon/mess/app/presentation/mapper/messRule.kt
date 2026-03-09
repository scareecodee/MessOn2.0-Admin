package com.messon.mess.app.presentation.mapper
import com.messon.mess.app.data.remote.dto.MessRuleDto
import com.messon.mess.app.domain.dataModel.MessRule

fun MessRule.toDto(): MessRuleDto{
    return MessRuleDto(
        day = day,
        isCompulsory = isCompulsory
    )
}


fun MessRuleDto.toDomain(): MessRule{
    return MessRule(
        day = day,
        isCompulsory = isCompulsory
    )
}