package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
    modifier: Modifier
){


}

@Composable
fun BtmNavBarItem(
    fontSize: Int,
    icon: ImageVector,
    color: Color,
    title: String,
    contentDescription: String
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription=contentDescription,
            modifier = Modifier.size(30.dp),
            tint = color
        )
        Text(
            text = title,
            fontSize = fontSize.sp
        )

    }
}



