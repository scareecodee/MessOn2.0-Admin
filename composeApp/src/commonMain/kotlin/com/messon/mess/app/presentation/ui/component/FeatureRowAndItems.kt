package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.theme.CustomColors.BlackberryCream
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun FeatureGrid(items: List<FeatureData>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(items) { feature ->
            FeatureItem(
                name = feature.name,
                icon = feature.icon,
                color = feature.color,
                modifier = Modifier,
                onClick =feature.onClick
            )
        }
    }
}

@Composable
fun FeatureItem(name: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier,onClick:()-> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                .border(1.dp, color.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = name, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            fontSize = 11.sp,
            color = BlackberryCream.copy(alpha = 0.9f),
            textAlign = TextAlign.Center,
            lineHeight = 13.sp,
            maxLines = 2,
            modifier = Modifier.width(64.dp)
        )
    }
}

@Composable
fun FeatureModule(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .shadow(2.dp, RoundedCornerShape(24.dp), spotColor = Color.Black.copy(alpha = 0.05f))
            .background(Color.White, RoundedCornerShape(24.dp))
            .border(1.dp, Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 14.sp,
            color = BlackberryCream,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
        )
        content()
    }
}


data class FeatureData(val name: String, val icon: ImageVector, val color: Color,var onClick:()->Unit)
