package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun CardHomeScreen(
    modifier: Modifier,
    icon: Painter,
    title: String,
    onCardClick:()-> Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.width(150.dp)
            .height(100.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                color = CustomColors.OffWhite,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .clickable{
                onCardClick()
            }

    ){
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(15.dp)
        ){
            Icon(
                painter = icon,
                contentDescription = "icon",
                modifier = Modifier.size(30.dp),
                tint = CustomColors.RichCerulean
            )
            Text(
                text = title,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}