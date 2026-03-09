package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuInputCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    cardBgColor: Color,
    footerTag: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(cardBgColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, modifier = Modifier.size(16.dp), tint = iconTint)
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = label,
                    fontSize =12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = CustomColors.BlackberryCream
                )
            }
            if (label.contains("Veg")) {
                Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFF86EFAC).copy(alpha = 0.8f), modifier = Modifier.size(16.dp))
            }
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = "Enter menu items...",
                    fontSize =12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium))
                )
                          },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.5f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.3f),
                focusedBorderColor = CustomColors.RichCerulean,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = CustomColors.BlackberryCream,
                unfocusedTextColor = CustomColors.BlackberryCream
            ),
            textStyle = TextStyle(
                fontSize =12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            minLines = 3
        )

        Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text(
                text = footerTag,
                modifier = Modifier.align(Alignment.BottomEnd),
                fontSize =12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = CustomColors.BlackberryCream.copy(alpha = 0.3f)
            )
        }
    }
}
