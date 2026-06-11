package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.DayConfigCard
import com.messon.mess.app.presentation.ui.state.FetchRulesState
import com.messon.mess.app.presentation.ui.state.SaveRulesState
import com.messon.mess.app.presentation.viewmodel.MessRuleVM
import com.messon.mess.app.theme.MessonTheme
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

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit){
        messRuleVM.fetchMessRule(weekType = WeekType.CURRENT_WEEK.value)
    }

    LaunchedEffect(fetchState) {
        when (fetchState) {
            is FetchRulesState.Success -> {
                showToast("Rules loaded successfully")
                messRuleVM.setInitialSchedule((fetchState as? FetchRulesState.Success)?.rules?:emptyList())
                messRuleVM.clearFetchRule()
            }
            is FetchRulesState.Error -> {
                showToast("Error loading mess rules")
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
                showToast("Schedule updated successfully")
                messRuleVM.clearSaveRule()
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
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
                .background(MessonTheme.colors.divider)
                .constrainAs(statusBarBox) { top.linkTo(parent.top) }
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
                tint = colors.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "Mess Schedule",
            fontSize = 18.sp,
            color = colors.onSurface,
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
                    bottom.linkTo(saveBtn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 95.dp)) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Configuration",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        color = colors.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.surface)
                            .border(1.dp, colors.outline.copy(alpha = 0.2f), CircleShape)
                            .padding(4.dp)
                    ) {
                        BulkActionChip(
                            "All Compulsory",
                            colors.primary
                        ) { messRuleVM.setAllCompulsory() }

                        BulkActionChip(
                            "All Optional",
                            colors.secondary
                        ) { messRuleVM.setAllOptional() }
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
        }

        Button(
            onClick = {
                val rules = messRuleVM.buildMessRules()
                messRuleVM.saveMessRule(rules, WeekType.CURRENT_WEEK.value)
            },
            modifier = Modifier
                .constrainAs(saveBtn) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
                .height(55.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text(
                "Save Schedule",
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold))
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