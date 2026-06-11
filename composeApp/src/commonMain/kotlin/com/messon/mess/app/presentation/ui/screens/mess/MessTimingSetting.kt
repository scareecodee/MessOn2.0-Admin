package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.mess.app.presentation.ui.component.TimeRow
import com.messon.mess.app.presentation.ui.state.MessTimingState
import com.messon.mess.app.presentation.viewmodel.MessTimingVM
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
fun MessTimeSettingScreen(
    onBackClick: () -> Unit,
    messTimingVM: MessTimingVM
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var isClosingSelected by remember { mutableStateOf(false) }
    val closingTime by messTimingVM.closingTime.collectAsState()
    val openingTime by messTimingVM.openingTime.collectAsState()
    val fetchTimingState by messTimingVM.fetchState.collectAsState()

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        messTimingVM.fetchMessTiming()
    }

    LaunchedEffect(fetchTimingState) {
        when (fetchTimingState) {
            is MessTimingState.Error -> {
                showToast(message = (fetchTimingState as MessTimingState.Error).message)
                messTimingVM.clearFetchState()
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(colors.background)
    ) {
        val (headerBg, topBar, titleSection, listRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .constrainAs(headerBg) { top.linkTo(parent.top) }
                .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                .background(colors.primary.copy(alpha = 0.9f))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp,
                    bottom = 15.dp
                )
                .constrainAs(topBar) { top.linkTo(parent.top) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ChevronLeft, null, tint = Color.White)
            }
            Text(
                "Admin Console",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            )
            IconButton(onClick = { }) {
                Icon(Icons.Default.MoreVert, null, tint = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(titleSection) {
                    top.linkTo(topBar.bottom, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                }
        ) {
            Text("Mess Timing", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily(Font(Res.font.publicsanssemibold)))
            Text("Set opening and closing time of mess", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp, fontFamily = FontFamily(Font(Res.font.publicsansmedium)))
        }

        LazyColumn(
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(headerBg.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(colors.surface)
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(44.dp).background(colors.primary.copy(0.1f), RoundedCornerShape(16.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Settings, null, tint = colors.primary)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Time Range",
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                        maxLines = 1,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text("Global Sync", fontSize = 10.sp, fontFamily = FontFamily(Font(Res.font.publicsansregular)), color = colors.onSurfaceVariant)
                                }
                            }

                            Surface(
                                color = colors.secondary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    "Active",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    color = colors.secondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        TimeRow(
                            label = "Opening",
                            time = openingTime?.to12HourTime() ?: "__",
                            icon = Icons.Default.CalendarToday,
                            iconColor = colors.primary,
                            onClick = { showTimePicker = true; isClosingSelected = false }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TimeRow(
                            label = "Closing",
                            time = closingTime?.to12HourTime() ?: "__",
                            icon = Icons.Default.Schedule,
                            iconColor = colors.primary,
                            onClick = { showTimePicker = true; isClosingSelected = true }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = { messTimingVM.saveMessTiming() },
                    modifier = Modifier.fillMaxWidth().height(55.dp).padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                ) {
                    Icon(Icons.Default.Sync, null, tint = Color.White, modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Save Changes",color=Color.White, fontSize = 14.sp, fontFamily = FontFamily(Font(Res.font.publicsanssemibold)))
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
        selectorProperties = WheelPickerDefaults.selectorProperties(enabled = true, borderColor = colors.primary),
        onDoneClick = {
            showTimePicker = false
            if (isClosingSelected) messTimingVM.updateClosing(it) else messTimingVM.updateOpening(it)
        },
        onDismiss = { showTimePicker = false },
        dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
        containerColor = MaterialTheme.colorScheme.surface
    )
}