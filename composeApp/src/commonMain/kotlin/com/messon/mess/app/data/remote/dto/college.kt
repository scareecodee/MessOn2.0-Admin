package com.messon.mess.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CollegeDto(
    @SerialName("college_id")
    val collegeId: String,

    @SerialName("college_name")
    val collegeName: String ,
)