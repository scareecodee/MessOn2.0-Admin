package com.messon.mess.app.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessRuleDto(
    @SerialName("day")
    val day: String,

    @SerialName("is_compulsory")
    val isCompulsory: Boolean,
)


