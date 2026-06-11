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
import androidx.compose.ui.draw.clip
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
    placeholderText: String = "",
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = modifier // Outer layout modifier belongs ONLY here
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
            modifier = Modifier // Uses fresh Modifier to separate outer sizing constraints from nested Row style
                .fillMaxWidth()
                .padding(horizontal = hPadding)
                .height(containerHeight)
                // Enforces that children are perfectly cropped within the rounded container shape, preventing sharp corners
                .clip(RoundedCornerShape(cornerRadius))
                .background(MaterialTheme.colorScheme.surface) // Adapts beautifully between light & dark surface modes
                .border(
                    borderThickness,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), // Clean border matching your active theme
                    RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = hPadding)
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant // Adapts color automatically to retain perfect visibility
            )

            Spacer(modifier = Modifier.width(spacerWidth))

            TextField(
                placeholder = {
                    Text(
                        text = placeholderText,
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                },
                value = query,
                onValueChange = { onQueryChange(it) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    // Set to Color.Transparent to let parent handle the curved background profile without rectangular layout bleed
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                textStyle = TextStyle(
                    fontSize = textFieldFont.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
            )
        }
    }
}