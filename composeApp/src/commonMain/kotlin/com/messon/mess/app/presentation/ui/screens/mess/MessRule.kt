package com.messon.mess.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.domain.dataModel.MessRule
import com.messon.mess.app.presentation.ui.component.DayConfigCard
import com.messon.mess.app.presentation.ui.state.FetchRulesState
import com.messon.mess.app.presentation.ui.state.SaveRulesState
import com.messon.mess.app.presentation.viewmodel.MessRuleVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.utils.DayStatus
import com.messon.mess.app.utils.WeekDay
import com.messon.mess.app.utils.WeekType
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun MessRuleScreen(
    onBackBtnClick: () -> Unit,
    messRuleVM: MessRuleVM
) {
    val daysOfWeek = WeekDay.entries
    val saveState by messRuleVM.saveRuleState.collectAsState()
    val fetchState by messRuleVM.fetchRuleState.collectAsState()
    val schedule by messRuleVM.schedule.collectAsState()

    LaunchedEffect(Unit){
        messRuleVM.fetchMessRule(weekType = WeekType.CURRENT_WEEK.value)
    }

    LaunchedEffect(fetchState) {
        when (fetchState) {

            is FetchRulesState.Success -> {
                showToast("success")

                messRuleVM.setInitialSchedule((fetchState as? FetchRulesState.Success)?.rules?:emptyList())

                messRuleVM.clearFetchRule()
            }

            is FetchRulesState.Error -> {
                showToast("error loading mess rules")
                messRuleVM.clearFetchRule()
            }

            else -> {}
        }
    }

    LaunchedEffect(saveState){
        when(saveState){
            is SaveRulesState.Error -> {
                showToast("Error while saving ")
                messRuleVM.clearSaveRule()
            }

            is SaveRulesState.Success->{
                showToast("Success")
                messRuleVM.clearSaveRule()        }

            else -> {}
        }
    }


    val compulsoryCount = schedule.values.count { it == DayStatus.Compulsory }
    val optionalCount = 7 - compulsoryCount

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {

        val (
            statusBarBox,
            backBtnRef,
            headingRef,
            listRef,
            saveBtn
        ) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = { onBackBtnClick() },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(
                Icons.Default.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "Mess Schedule",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(listRef) {
                    top.linkTo(backBtnRef.bottom, margin = 10.dp)
                    bottom.linkTo(saveBtn.top, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 5.dp)
        ) {

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Configuration",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        color = CustomColors.BlackberryCream
                    )

                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(
                                1.dp,
                                CustomColors.LightGrayBlue.copy(0.3f),
                                CircleShape
                            )
                            .padding(4.dp)
                    ) {

                        BulkActionChip(
                            "All Compulsory",
                            CustomColors.RichCerulean
                        ) {
                            messRuleVM.setAllCompulsory()                        }

                        BulkActionChip(
                            "All Optional",
                            CustomColors.HoneyBronze
                        ) {
                            messRuleVM.setAllOptional()                        }
                    }
                }
            }

            items(daysOfWeek) { day ->

                val status = schedule[day] ?: DayStatus.Compulsory

                DayConfigCard(
                    day = day.value.replaceFirstChar { it.uppercase() },
                    status = status,
                    onStatusChange = { newStatus ->
                        messRuleVM.updateDayStatus(day, newStatus)
                    }
                )
            }

            item {
                Spacer(Modifier.height(60.dp))
            }
        }

        Button(
            onClick = {
                val rules = messRuleVM.buildMessRules()
                messRuleVM.saveMessRule(rules, WeekType.CURRENT_WEEK.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .constrainAs(saveBtn) {
                    start.linkTo(parent.start, margin = 5.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.RichCerulean
            ),
            shape = RoundedCornerShape(30.dp)
        ) {

            Icon(Icons.Default.CheckCircle, contentDescription = null)

            Spacer(Modifier.width(8.dp))

            Text(
                "Save Schedule",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold))
            )
        }
    }
}
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = color
            )
            Text(
                text = label,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BulkActionChip(label: String, color: Color, onClick: () -> Unit) {
    Text(
        text = label,
        fontSize = 10.sp,
        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
        color = color,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

