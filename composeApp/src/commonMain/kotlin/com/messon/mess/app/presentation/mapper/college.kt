package com.messon.mess.app.presentation.mapper

import com.messon.mess.app.data.remote.dto.CollegeDto
import com.messon.mess.app.domain.dataModel.College


fun CollegeDto.toDomain(): College {
    return College(
        collegeId = collegeId,
        collegeName =collegeName
    )
}