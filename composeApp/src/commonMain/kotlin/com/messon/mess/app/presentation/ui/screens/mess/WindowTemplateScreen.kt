package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.TempDaySelector
import com.messon.mess.app.presentation.ui.component.TimeRow
import com.messon.mess.app.presentation.ui.state.WindowTemplateState
import com.messon.mess.app.presentation.viewmodel.WindowTemplateVM
import com.messon.mess.app.theme.MessonTheme
import com.messon.mess.app.utils.to12HourTime
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.jetbrains.compose.resources.Font

@Composable
fun WindowTemplateScreen(
    onBackClick: () -> Unit,
    onSpecialWindowClick: () -> Unit,
    windowTemplateVM: WindowTemplateVM
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var isClosingSelected by remember { mutableStateOf(false) }

    val closingTime by windowTemplateVM.closingTime.collectAsState()
    val openingTime by windowTemplateVM.openingTime.collectAsState()
    val startDay by windowTemplateVM.openingDay.collectAsState()
    val endDay by windowTemplateVM.closingDay.collectAsState()

    val saveState by windowTemplateVM.saveTempState.collectAsState()
    val fetchState by windowTemplateVM.fetchTempState.collectAsState()

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) { windowTemplateVM.fetchWindowTemplate() }

    LaunchedEffect(fetchState) {
        if (fetchState is WindowTemplateState.Error) {
            showToast((fetchState as WindowTemplateState.Error).message)
            windowTemplateVM.clearFetchState()
        }
    }

    LaunchedEffect(saveState) {
        if (saveState is WindowTemplateState.Error) {
            showToast((saveState as WindowTemplateState.Error).message)
            windowTemplateVM.clearSaveState()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        val (statusBar, listRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding())
                .background(MessonTheme.colors.divider)
                .constrainAs(statusBar) { top.linkTo(parent.top) }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(statusBar.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (
                        backBtnRef, headingRef, iconBox, title, subtitle, description,
                        startLabel, startDropdown, endLabel, endDropdown,
                        startTimeRef, closeTimeRef, applyBtn, specialBtn
                    ) = createRefs()

                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.constrainAs(backBtnRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        }
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "back", tint = colors.onSurface)
                    }

                    Text(
                        "Window Setting",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                        color = colors.onSurface,
                        modifier = Modifier.constrainAs(headingRef) {
                            start.linkTo(backBtnRef.end, 5.dp)
                            centerVerticallyTo(backBtnRef)
                        }
                    )

                    Icon(
                        imageVector = Icons.Default.Celebration,
                        contentDescription = "special window",
                        modifier = Modifier
                            .constrainAs(specialBtn) {
                                end.linkTo(parent.end, margin = 16.dp)
                                centerVerticallyTo(backBtnRef)
                            }
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color = colors.primary.copy(alpha = 0.1f), shape = CircleShape)
                            .clickable { onSpecialWindowClick() },
                        tint = colors.primary,
                    )

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(colors.primaryContainer, RoundedCornerShape(12.dp))
                            .constrainAs(iconBox) {
                                top.linkTo(backBtnRef.bottom, 15.dp)
                                start.linkTo(parent.start)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Schedule, null, tint = colors.primary)
                    }

                    Text(
                        "Meal Preference Window",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        color = colors.onSurface,
                        modifier = Modifier.constrainAs(title) {
                            top.linkTo(iconBox.top)
                            start.linkTo(iconBox.end, 12.dp)
                        }
                    )

                    Text(
                        "Weekly automated scheduling",
                        color = colors.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        modifier = Modifier.constrainAs(subtitle) {
                            top.linkTo(title.bottom)
                            start.linkTo(title.start)
                        }
                    )

                    Text(
                        "Configure the default window for students to submit preferences.",
                        color = colors.onSurfaceVariant.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                        modifier = Modifier.constrainAs(description) {
                            top.linkTo(iconBox.bottom, 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        "Opening Day",
                        color = colors.onSurfaceVariant,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        modifier = Modifier.constrainAs(startLabel) {
                            top.linkTo(description.bottom, 20.dp)
                            start.linkTo(endDropdown.start)
                            end.linkTo(endDropdown.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    TempDaySelector(
                        selectedDay = startDay ?: "__",
                        onDaySelected = { windowTemplateVM.updateOpeningDay(it) },
                        modifier = Modifier.constrainAs(startDropdown) {
                            top.linkTo(startLabel.bottom, 8.dp)
                            start.linkTo(endDropdown.start)
                            end.linkTo(endDropdown.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Text(
                        "Closing Day",
                        color = colors.onSurfaceVariant,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        modifier = Modifier.constrainAs(endLabel) {
                            top.linkTo(startDropdown.bottom, 16.dp)
                            start.linkTo(endDropdown.start)
                        }
                    )

                    TempDaySelector(
                        selectedDay = endDay ?: "__",
                        onDaySelected = { windowTemplateVM.updateClosingDay(it) },
                        modifier = Modifier.constrainAs(endDropdown) {
                            top.linkTo(endLabel.bottom, 8.dp)
                            start.linkTo(parent.start, margin = 5.dp)
                            end.linkTo(parent.end, margin = 5.dp)
                            width = Dimension.fillToConstraints
                        }
                    )

                    TimeRow(
                        label = "Opening Time",
                        time = openingTime?.to12HourTime() ?: "__",
                        icon = Icons.Default.CalendarToday,
                        iconColor = colors.secondary,
                        onClick = {
                            showTimePicker = true
                            isClosingSelected = false
                        },
                        modifier = Modifier.constrainAs(startTimeRef) {
                            top.linkTo(endDropdown.bottom, 30.dp)
                            start.linkTo(endDropdown.start)
                            end.linkTo(endDropdown.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    TimeRow(
                        label = "Closing Time",
                        time = closingTime?.to12HourTime() ?: "__",
                        icon = Icons.Default.Schedule,
                        iconColor = colors.primary,
                        onClick = {
                            showTimePicker = true
                            isClosingSelected = true
                        },
                        modifier = Modifier.constrainAs(closeTimeRef) {
                            top.linkTo(startTimeRef.bottom, 30.dp)
                            start.linkTo(endDropdown.start)
                            end.linkTo(endDropdown.end)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary,
                            contentColor = colors.onPrimary
                        ),
                        onClick = { windowTemplateVM.saveWindowTemplate() },
                        modifier = Modifier
                            .height(55.dp)
                            .constrainAs(applyBtn) {
                                top.linkTo(closeTimeRef.bottom, 35.dp)
                                start.linkTo(parent.start, margin = 10.dp)
                                end.linkTo(parent.end, margin = 10.dp)
                                width = Dimension.fillToConstraints
                            },
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            "Apply Settings",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.Sync, null, modifier = Modifier.size(25.dp))
                    }
                }
            }
        }

        WheelTimePickerView(
            modifier = Modifier.safeContentPadding(),
            title = if (isClosingSelected) "Set Closing Time" else "Set Opening Time",
            timeFormat = TimeFormat.AM_PM,
            titleStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 16.sp),
            doneLabelStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 16.sp),
            showTimePicker = showTimePicker,
            height = 220.dp,
            shape = RoundedCornerShape(30.dp),
            defaultTextStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsansmedium)), fontSize = 12.sp),
            selectedTextStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 14.sp),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                enabled = true,
                borderColor = colors.primary
            ),
            onDoneClick = {
                showTimePicker = false
                if (isClosingSelected) windowTemplateVM.updateClosingTime(it) else windowTemplateVM.updateOpeningTime(it)
            },
            onDismiss = { showTimePicker = false },
            dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
        )
    }
}