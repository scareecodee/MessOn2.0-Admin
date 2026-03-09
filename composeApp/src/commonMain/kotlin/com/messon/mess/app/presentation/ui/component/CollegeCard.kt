package com.messon.mess.app.presentation.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun CollegeCard(
    collegeName: String,
    onClick: () -> Unit = {}
) {

    val backgroundColor = remember {
        listOf(
            CustomColors.LightBlue,
            CustomColors.Cream,
            CustomColors.LightLavender,
        ).random()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .heightIn(min = 85.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(2.dp, Color.White, RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (iconBox, nameText, arrowBtn, backgroundDecor) = createRefs()

            Icon(
                imageVector = Icons.Default.Business,
                contentDescription = null,
                tint = CustomColors.BlackberryCream.copy(alpha = 0.05f),
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(backgroundDecor) {
                        top.linkTo(parent.top, margin = (-24).dp)
                        end.linkTo(parent.end, margin = (-16).dp)
                    }
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .constrainAs(iconBox) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "College Icon",
                    tint = CustomColors.RichCerulean,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = collegeName,
                color = CustomColors.BlackberryCream,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                modifier = Modifier.constrainAs(nameText) {
                    start.linkTo(iconBox.end, margin = 16.dp)
                    end.linkTo(arrowBtn.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .constrainAs(arrowBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = CustomColors.RichCerulean,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}