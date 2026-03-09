package com.messon.mess.app.utils

import com.messon.mess.app.domain.dataModel.MessRule

 fun convertRulesToSchedule(rules: List<MessRule>): Map<WeekDay, DayStatus> {
    return rules.associate { rule ->

        val day = WeekDay.entries.first { it.value == rule.day }

        day to if (rule.isCompulsory)
            DayStatus.Compulsory
        else
            DayStatus.Optional
    }
}