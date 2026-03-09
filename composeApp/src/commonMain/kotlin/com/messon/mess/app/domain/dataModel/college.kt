package com.messon.mess.app.domain.dataModel



data class CollegeAdmin(
    val id: Int,
    val name: String,
    val email: String = "",
    val isActive: Boolean = true
)

data class College(
    val collegeId: String,
    val collegeName: String ,
)