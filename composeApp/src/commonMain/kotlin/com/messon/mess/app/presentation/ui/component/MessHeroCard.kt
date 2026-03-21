package com.messon.mess.app.presentation.ui.component
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MessHeroCard(name: String, location: String,modifier: Modifier) {
    Card(
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(start =12.dp, end = 12.dp),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            val (locTag, title, subtitle, bgIcon) = createRefs()
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .alpha(0.05f)
                    .constrainAs(bgIcon) {
                        top.linkTo(parent.top, margin = (-20).dp)
                        end.linkTo(parent.end, margin = (-20).dp)
                    },
                tint = CustomColors.BlackberryCream
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(locTag) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            ) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    tint = CustomColors.RichCerulean,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = CustomColors.RichCerulean,
                )
            }

            Text(
                text = name,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = Color.Black,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(locTag.bottom, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = "Schedule Management",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = Color.LightGray,
                letterSpacing = 1.sp,
                modifier = Modifier.constrainAs(subtitle) {
                    top.linkTo(title.bottom, 8.dp)
                    start.linkTo(parent.start)
                }
            )
        }
    }
}

