package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.platform.annotation.SuppressLint
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import org.jetbrains.compose.resources.Font


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SearchBar(
    placeholderText: String="",
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val hPadding = maxWidth * 0.04f
        val containerHeight = maxHeight * 0.064f
        val cornerRadius = maxWidth * 0.075f
        val borderThickness = maxWidth * 0.00125f
        val iconSize = maxWidth * 0.06f
        val spacerWidth = maxWidth * 0.02f
        val textFieldFont = maxWidth.value * 0.035f
        val placeholderFont = maxWidth.value * 0.03f
        val dropdownFont = maxWidth.value * 0.037f

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =modifier
                .fillMaxWidth()
                .padding(horizontal = hPadding)
                .height(containerHeight)
                .background(
                    Color.White,
                    RoundedCornerShape(cornerRadius)
                )
                .border(
                    borderThickness,
                    Color.DarkGray,
                    RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = hPadding)
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(spacerWidth))


            TextField(
                placeholder = {
                    Text(
                        text = placeholderText,
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp
                    )
                },
                value = query,
                onValueChange = { onQueryChange(it) },
                modifier = Modifier.weight(1f)
                ,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                textStyle = TextStyle(
                    fontSize = textFieldFont.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = Color.Black
                ),
                singleLine = true,
            )
        }
    }
}