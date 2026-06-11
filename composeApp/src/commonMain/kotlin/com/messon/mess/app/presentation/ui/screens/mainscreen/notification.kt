package com.messon.mess.app.presentation.ui.screens.mainscreen
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import messon.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.messon.mess.app.presentation.ui.component.EachNotificationItem
import messon.composeapp.generated.resources.publicsansbold


@Composable
fun NotificationScreen(
    modifier: Modifier,
    onBackBtnClick:()->Unit,
    onFabClick:()->Unit
){
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        val (statusBarBox,headingRef,listRef,backBtnRef,fabRef)=createRefs()
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()+5.dp)
                .background(MaterialTheme.colorScheme.outline)
                .constrainAs(statusBarBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef){
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ){
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                tint =MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text ="Notifications",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )



        LazyColumn(
            modifier = Modifier.constrainAs(listRef){
                top.linkTo(headingRef.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                height= Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(7){
                EachNotificationItem(
                    subject = "Today's Special!Today's Special!Today's Special!Today's Special!",
                    description = "Hyderabadi Chicken Biryani will be served for Sunday Lunch. Don't miss it!" ,
                    iconColor = MaterialTheme.colorScheme.primary.copy(0.7f),
                    modifier = Modifier,
                    createdTime = "10 min ago",
                    painter = rememberVectorPainter(Icons.Outlined.Fastfood),
                    contentDescription = "",
                )
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = onFabClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            ),
            modifier = Modifier.constrainAs(fabRef) {
                end.linkTo(parent.end, margin = 20.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "send notification",
                modifier = Modifier.size(30.dp)
            )
        }

    }

}