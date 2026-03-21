package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.mess.app.theme.CustomColors
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.messon.mess.app.presentation.ui.component.TimeRow
import com.messon.mess.app.presentation.ui.state.PrefWindowState
import com.messon.mess.app.presentation.viewmodel.PrefWindowVM
import com.messon.mess.app.utils.WeekType
import com.messon.mess.app.utils.toHumanReadable
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.jetbrains.compose.resources.Font

@Composable
fun MealPrefWindow(
    onBackClick: () -> Unit,
    prefWindowVM: PrefWindowVM
) {
    var showDateTimePicker by remember{ mutableStateOf(false) }
    var isClosingSelected by remember { mutableStateOf(false) }


    val openingTime by prefWindowVM.openingTime.collectAsState()
    val closingTime by prefWindowVM.closingTime.collectAsState()
    val windowStatus by prefWindowVM.windowStatus.collectAsState()

    val savePrefWindowState by prefWindowVM.saveState.collectAsState()
    val fetchState by prefWindowVM.fetchState.collectAsState()
    val isSaving = savePrefWindowState is PrefWindowState.Loading


    LaunchedEffect(Unit) {
        prefWindowVM.fetchPrefWindow(WeekType.CURRENT_WEEK)
    }

    LaunchedEffect(fetchState) {
        when (fetchState) {
            is PrefWindowState.Error -> {
                showToast((fetchState as PrefWindowState.Error).message)
            }
            else -> {}
        }
    }

    LaunchedEffect(savePrefWindowState){
        when(savePrefWindowState){
            is PrefWindowState.Error -> {
                showToast(message = (savePrefWindowState as PrefWindowState.Error).message)
            }

            else -> {}
        }
    }


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {

        val (headerBg, topBar, titleSection,listRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .constrainAs(headerBg) {
                    top.linkTo(parent.top)
                }
                .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
                .background(CustomColors.RichCerulean.copy(0.9f))
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
                .constrainAs(topBar) {
                    top.linkTo(parent.top)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick,
            ) {
                Icon(Icons.Default.ChevronLeft, null, tint = Color.White)
            }

            Text(
                "Admin Console",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            )

            IconButton(
                onClick = { },
            ) {
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
            Text(
                "Meal Window",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            )

            Text(
                "Configure global preference accessibility.",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            )
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
                        .background(Color.White)
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
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(
                                            CustomColors.LightBlue,
                                            RoundedCornerShape(16.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.Settings,
                                        null,
                                        tint = CustomColors.RichCerulean
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        "Time Range",
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        "Global Sync",
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                                        color = Color.Gray
                                    )
                                }
                            }

                            Surface(
                                color = CustomColors.HoneyBronze.copy(0.1f),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                   if(windowStatus) "Active" else "Inactive",
                                    modifier = Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 4.dp
                                    ),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    color = CustomColors.HoneyBronze
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        TimeRow(
                            label = "Opening",
                            time = openingTime?.toHumanReadable() ?: "--",
                            icon = Icons.Default.CalendarToday,
                            iconColor = CustomColors.HoneyBronze,
                            onClick = {
                                showDateTimePicker = true
                                isClosingSelected = false
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        TimeRow(
                            label = "Closing",
                            time = closingTime?.toHumanReadable() ?: "--",
                            icon = Icons.Default.Schedule,
                            iconColor = CustomColors.RichCerulean,
                            onClick =
                                {
                                    showDateTimePicker = true
                                    isClosingSelected = true

                                }
                        )
                    }
                }

            }

            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Info,
                            null,
                            tint = CustomColors.RichCerulean,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {

                        Text(
                            "Admin Tip",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                            color = CustomColors.BlackberryCream
                        )

                        Text(
                            "Unified deadline for Breakfast, Lunch, and Dinner. Ensure sufficient time for kitchen prep.",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    enabled = !isSaving ,
                    onClick = {
                        prefWindowVM.savePrefWindow(WeekType.CURRENT_WEEK)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.RichCerulean,
                        disabledContainerColor = CustomColors.RichCerulean
                    )
                ) {

                    Icon(Icons.Default.Sync, null)

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "Save Changes",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    )
                }
            }
            }
        }

    WheelDateTimePickerView(
        modifier = Modifier.safeContentPadding(),
        title =if(isClosingSelected) "Set Closing Time" else "Set Opening Time",
        timeFormat = TimeFormat.AM_PM,
        titleStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 16.sp
        ),
        doneLabelStyle =TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 16.sp
        ),
        showDatePicker =showDateTimePicker,
        height = 220.dp,
        yearsRange = IntRange(2026,2122),
        shape = RoundedCornerShape(30.dp),
        defaultDateTextStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            fontSize = 12.sp
        ),
        selectedDateTextStyle = TextStyle(
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 14.sp
        ),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            enabled=true,
            borderColor = CustomColors.RichCerulean
        ),
        onDoneClick = {
            showDateTimePicker=false
            if (isClosingSelected) {
                prefWindowVM.updateClosing(it)
            } else {
                prefWindowVM.updateOpening(it)
            }
                      },
        onDismiss = {showDateTimePicker=false},
        dateTimePickerView= DateTimePickerView.BOTTOM_SHEET_VIEW,
    )
}


