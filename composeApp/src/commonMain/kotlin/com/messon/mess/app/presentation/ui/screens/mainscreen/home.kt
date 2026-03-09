package com.messon.mess.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CircleNotifications
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.MoodBad
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Reviews
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.CardHomeScreen
import com.messon.mess.app.presentation.ui.component.CircularProgressBar
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeScreen(
    modifier: Modifier,
    profileImg: Painter,
    greeting: String,
    onBellIconClick:()->Unit,
    onMessMenuClick:()->Unit,
    onComplaintsClick:()->Unit,
    onReviewsClick:()->Unit,
    onNotificationsClick:()->Unit
) {
    val radius by remember { mutableStateOf(35)}
    val animationDuration by remember { mutableStateOf(2000) }
    val animationDelay by remember { mutableStateOf(700)}
    val annotString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 17.sp
            )
        ) {
            append("Your")
        }
        withStyle(
            style = SpanStyle(
                fontSize = 28.sp
            )
        ) {
            append("\nScan's")
        }
        withStyle(
            style = SpanStyle(
                fontSize = 17.sp
            )
        ) {
            append(" Today")
        }
    }
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        val (statusBarBox,profileImgRef, bellIconRef, greetingRef, lazyColumnRef,annotStringRef) = createRefs()

        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()+5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        Image(
            painter = profileImg,
            contentDescription = "Profile Image",
            modifier = Modifier.size(40.dp)
                .constrainAs(profileImgRef) {
                    start.linkTo(parent.start, margin = 20.dp)
                    top.linkTo(statusBarBox.bottom, margin = 30.dp)
                }
        )

        Text(
            text = greeting,
            fontSize = 16.sp,
            color = Color.LightGray,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            modifier = Modifier.constrainAs(greetingRef) {
                start.linkTo(profileImgRef.end)
                top.linkTo(profileImgRef.top)
                bottom.linkTo(profileImgRef.bottom)
            }
                .padding(horizontal = 10.dp)
        )
        Icon(
            imageVector = Icons.Outlined.CircleNotifications,
            contentDescription = "bell icon",
            modifier = Modifier.constrainAs(bellIconRef) {
                end.linkTo(parent.end, margin = 20.dp)
                top.linkTo(profileImgRef.top)
                bottom.linkTo(profileImgRef.bottom)
            }
                .size(30.dp)
                .clip(CircleShape)
                .clickable{
                    onBellIconClick()
                }
        )

        Text(
            lineHeight =38.sp,
            text = annotString,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(annotStringRef){
                start.linkTo(profileImgRef.start)
                top.linkTo(profileImgRef.bottom, margin = 5.dp)
            }
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )
        LazyColumn(
            modifier = Modifier.constrainAs(lazyColumnRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(annotStringRef.bottom, margin = 20.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                        .fillMaxWidth()
                ) {
                    CircularProgressBar(
                        modifier = Modifier,
                        percentage = 1f,
                        radius = radius.dp,
                        color = CustomColors.RichCerulean.copy(alpha = 0.9f),
                        strokeWidth = 6.dp,
                        animationDuration = animationDuration,
                        animationDelay = animationDelay,
                        numbStudents = 256,
                        mealSlot = "Breakfast"
                    )

                    CircularProgressBar(
                        modifier = Modifier,
                        percentage = 0.8f,
                        radius =radius.dp,
                        color = CustomColors.HoneyBronze.copy(alpha = 0.9f),
                        strokeWidth = 6.dp,
                        animationDuration = animationDuration,
                        animationDelay = animationDelay,
                        numbStudents = 256,
                        mealSlot = "Lunch"
                    )

                    CircularProgressBar(
                        modifier = Modifier,
                        percentage = 0.8f,
                        radius = radius.dp,
                        color = CustomColors.BlackberryCream.copy(alpha = 0.9f),
                        strokeWidth = 6.dp,
                        animationDuration = animationDuration,
                        animationDelay = animationDelay,
                        numbStudents = 256,
                        mealSlot = "Dinner"
                    )
                }
            }

            item {
                Text(
                    text = "Quick Access",
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    fontSize = 16.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardHomeScreen(
                        modifier = Modifier,
                        icon =rememberVectorPainter( Icons.Outlined.Notifications),
                        title = "Notifications",
                        onCardClick = onNotificationsClick

                    )
                    CardHomeScreen(
                        modifier = Modifier,
                        icon =rememberVectorPainter( Icons.Outlined.MenuBook),
                        title = "Mess Menu",
                        onCardClick = onMessMenuClick
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardHomeScreen(
                        modifier = Modifier,
                        icon =rememberVectorPainter(Icons.Outlined.MoodBad),
                        title = "Complaints",
                        onCardClick = onComplaintsClick
                    )
                    CardHomeScreen(
                        modifier = Modifier,
                        icon = rememberVectorPainter(Icons.Outlined.Reviews),
                        title = "Reviews",
                        onCardClick = onReviewsClick
                    )
                }
            }

        }
    }
}