package com.messon.mess.app.presentation.ui.screens.mess

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.domain.dataModel.Menu
import com.messon.mess.app.presentation.ui.component.MealCard
import com.messon.mess.app.presentation.ui.component.MealTypeSelector
import com.messon.mess.app.presentation.ui.state.FetchMenuState
import com.messon.mess.app.presentation.viewmodel.MenuFetchVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.utils.MealSlot
import com.messon.mess.app.utils.convertDay
import com.messon.mess.app.utils.getDish
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewMessMenuScreen(
    onBackBtnClick:()-> Unit,
    onEditBtnClick:()-> Unit,
    menuFetchVM: MenuFetchVM
) {

    var selectedDay by remember { mutableStateOf("Mon") }
    var dietTab by remember { mutableStateOf(0) }

    val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
    val uiState by menuFetchVM.uiState.collectAsState()
    val menu by menuFetchVM.menu.collectAsState()
    LaunchedEffect(Unit) {
        menuFetchVM.fetchMenu()
    }
    LaunchedEffect(uiState) {
        when (uiState) {

            is FetchMenuState.Error -> {
                showToast("Error while loading menu")
            }

            else -> {}
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {

        val (scrollContent,statusBarBox,backBtnRef,headingRef,editBtnRef) = createRefs()

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
            text = "Mess Menu",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        IconButton(
            onClick = onEditBtnClick,
            modifier = Modifier
                .constrainAs(editBtnRef) {
                    end.linkTo(parent.end, margin = 15.dp)
                    centerVerticallyTo(backBtnRef)
                }
                .padding(horizontal = 10.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    color = CustomColors.RichCerulean.copy(0.2f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "edit",
                tint = CustomColors.RichCerulean.copy(0.8f),
                modifier = Modifier.size(18.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(scrollContent){
                    top.linkTo(backBtnRef.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 12.dp)
        ){

            item {
                LazyRow(
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){

                    items(days){ day ->

                        val active = selectedDay == day

                        val scale by animateFloatAsState(
                            targetValue = if(active) 1.1f else 1f, label = ""
                        )

                        val bg by animateColorAsState(
                            targetValue = if(active) CustomColors.RichCerulean else Color.White,
                            label = ""
                        )

                        val textColor by animateColorAsState(
                            targetValue = if(active) Color.White else CustomColors.BlackberryCream,
                            label = ""
                        )

                        Card(
                            modifier = Modifier
                                .size(64.dp,80.dp)
                                .scale(scale)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ){ selectedDay = day },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(bg),
                            elevation = CardDefaults.cardElevation(
                                if(active) 12.dp else 0.dp
                            )
                        ){
                            Box(
                                Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    day,
                                    fontSize =12.sp,
                                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }


            item{
                Spacer(Modifier.height(12.dp))

            }

            item {
                MealTypeSelector(
                    selected = dietTab,
                    onSelect = { dietTab = it }
                )
            }

            item{
                Spacer(Modifier.height(24.dp))
            }

            item {
                MealCard(
                    mealSlot = "Breakfast",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.BREAKFAST.value, dietTab),
                    tint = CustomColors.LightBlue,
                    accent = CustomColors.RichCerulean,
                    Icons.Default.Coffee
                )
            }

            item{
                MealCard(
                    mealSlot = "Lunch",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.LUNCH.value, dietTab),
                    tint = CustomColors.Cream,
                    accent = CustomColors.HoneyBronze,
                    Icons.Default.WbSunny
                )
            }


            item{
                MealCard(
                    mealSlot = "Dinner",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.DINNER.value, dietTab),
                    tint = CustomColors.LightLavender,
                    accent = CustomColors.BlackberryCream,
                    Icons.Default.NightsStay
                )
            }


            item{
                Spacer(Modifier.height(100.dp))

            }
        }
        if (uiState is FetchMenuState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(createRef()) {
                    centerTo(parent)
                }
            )
        }
    }
}

