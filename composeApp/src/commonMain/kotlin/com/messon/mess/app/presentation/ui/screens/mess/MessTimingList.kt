package com.messon.mess.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.domain.dataModel.MessTiming
import com.messon.mess.app.presentation.ui.component.MessHeroCard
import com.messon.mess.app.presentation.ui.component.TimingCard
import com.messon.mess.app.presentation.ui.state.MessTimingState
import com.messon.mess.app.presentation.viewmodel.MessTimingVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.theme.CustomColors.LightGrayBlue
import com.messon.mess.app.utils.MealSlot
import com.messon.mess.app.utils.toDisplayTime
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun MessTimingScreen(
    onBackBtnClick:()-> Unit,
    onCardClick:()-> Unit,
    messTimingVM: MessTimingVM
) {

    val fetchTimingState by messTimingVM.fetchState.collectAsState()

    LaunchedEffect(Unit){
        messTimingVM.fetchMessTiming()
    }
    LaunchedEffect(fetchTimingState){
        when(fetchTimingState){
            is MessTimingState.Error -> {
                showToast(message = (fetchTimingState as MessTimingState.Error).message)
                messTimingVM.clearFetchState()
            }

            else -> {}
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val ( list,heroCardRef,statusBarBox,backBtnRef,headingRef,progressIndicator) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )


        IconButton(
            onClick = {
                onBackBtnClick()
            },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp)
            )
        }


        Text(
            text = "Mess Timing",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        MessHeroCard(
            name = "Main Campus Mess",
            location = "Central Block, Ground Floor",
            modifier = Modifier.constrainAs(heroCardRef){
                top.linkTo(backBtnRef.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )


        if(fetchTimingState is MessTimingState.Loading ){
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressIndicator){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(heroCardRef.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        if(fetchTimingState is MessTimingState.Success){
            val data = (fetchTimingState as MessTimingState.Success).messTiming
            val breakfast = data.getBySlot(MealSlot.BREAKFAST.value)
            val lunch = data.getBySlot(MealSlot.LUNCH.value)
            val dinner = data.getBySlot(MealSlot.DINNER.value)
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .constrainAs(list){
                        top.linkTo(heroCardRef.bottom, margin = 10.dp)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width= Dimension.fillToConstraints
                        height= Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {

                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint =Color.LightGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "INTERVALS",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                            color = Color.LightGray,
                        )
                    }
                }

                item {
                    TimingCard(
                        title = MealSlot.BREAKFAST.value.replaceFirstChar { it.uppercaseChar() },
                        time =breakfast?.toDisplayTime()?:"__",
                        icon = Icons.Default.Coffee,
                        iconBgColor = CustomColors.LightBlue,
                        iconTint = CustomColors.RichCerulean,
                        onCardClick = {
                            messTimingVM.setMealSlot(MealSlot.BREAKFAST.value)
                            onCardClick()
                        }
                    )
                }
                item {
                    TimingCard(
                        title = MealSlot.LUNCH.value.replaceFirstChar { it.uppercaseChar() },
                        time =lunch?.toDisplayTime()?:"__",
                        icon = Icons.Default.WbSunny,
                        iconBgColor = CustomColors.Cream,
                        iconTint = CustomColors.HoneyBronze,
                        onCardClick = {
                            messTimingVM.setMealSlot(MealSlot.LUNCH.value)
                            onCardClick()
                        }
                    )
                }
                item {
                    TimingCard(
                        title = MealSlot.DINNER.value.replaceFirstChar { it.uppercaseChar() },
                        time =dinner?.toDisplayTime()?:"__",
                        icon = Icons.Default.NightsStay,
                        iconBgColor = CustomColors.LightLavender,
                        iconTint = CustomColors.BlackberryCream,
                        onCardClick = {
                            messTimingVM.setMealSlot(MealSlot.DINNER.value)
                            onCardClick()
                        }
                    )
                }
            }
        }
    }
}

fun List<MessTiming>.getBySlot(slot: String): MessTiming? {
    return this.find { it.mealSlot.equals(slot, ignoreCase = true) }
}