package com.messon.mess.app.presentation.ui.screens.mainscreen
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material.icons.outlined.Info
//import androidx.compose.material.icons.outlined.Logout
//import androidx.compose.material.icons.outlined.Notifications
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.constraintlayout.compose.Dimension
//import coil3.compose.AsyncImage
//import messon.composeapp.generated.resources.Res
//import org.jetbrains.compose.resources.Font
//
//@Composable
//fun ProfileScreen(
//    user: User,
//    modifier: Modifier,
//    onNotificationClick: () -> Unit = {},
//    onAboutUsClick: () -> Unit = {},
//    onLogoutClick: () -> Unit = {},
//) {
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.background)
//    ) {
//        val (statusBarBox, headingRef, profileImageRef, itemListRef, nameRef, mailIdRef) = createRefs()
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
//                .background(MaterialTheme.colorScheme.background)
//                .constrainAs(statusBarBox) {
//                    top.linkTo(parent.top)
//                    end.linkTo(parent.end)
//                }
//        )
//
//        Text(
//            text = "Profile",
//            fontSize = 24.sp,
//            color = MaterialTheme.colorScheme.onBackground,
//            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
//            modifier = Modifier
//                .constrainAs(headingRef) {
//                    start.linkTo(parent.start, margin = 24.dp)
//                    top.linkTo(statusBarBox.bottom, margin = 20.dp)
//                }
//        )
//
//        Box(
//            modifier = Modifier
//                .constrainAs(profileImageRef) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(headingRef.bottom, margin = 32.dp)
//                }
//                .size(108.dp)
//                .shadow(elevation = 6.dp, shape = CircleShape)
//                .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
//                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape),
//            contentAlignment = Alignment.Center
//        ) {
//            AsyncImage(
//                model = user.profileImg,
//                contentDescription = "profile image",
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxSize()
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Text(
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            text = user.name ?: "",
//            fontSize = 18.sp,
//            color = MaterialTheme.colorScheme.onBackground,
//            fontFamily = FontFamily(Font(Res.font.intersemibold)),
//            modifier = Modifier
//                .constrainAs(nameRef) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(profileImageRef.bottom, margin = 16.dp)
//                }
//                .padding(horizontal = 24.dp)
//        )
//
//        Text(
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//            text = user.mailId,
//            fontSize = 13.sp,
//            color = MaterialTheme.colorScheme.onSurfaceVariant,
//            fontFamily = FontFamily(Font(Res.font.intersemibold)),
//            modifier = Modifier
//                .constrainAs(mailIdRef) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    top.linkTo(nameRef.bottom, margin = 4.dp)
//                }
//                .padding(horizontal = 24.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier
//                .constrainAs(itemListRef) {
//                    top.linkTo(mailIdRef.bottom, margin = 24.dp)
//                    bottom.linkTo(parent.bottom)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    width = Dimension.fillToConstraints
//                    height = Dimension.fillToConstraints
//                }
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp, vertical = 8.dp),
//            contentPadding = PaddingValues(bottom = 32.dp)
//        ) {
//            item {
//                Text(
//                    color = MaterialTheme.colorScheme.primary,
//                    fontSize = 12.sp,
//                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
//                    text = "SETTINGS",
//                    letterSpacing = 1.sp,
//                    modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 8.dp)
//                )
//            }
//
//            // Modern rounded grouped card container for Settings
//            item {
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(20.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
//                ) {
//                    Column {
//                        EachItemProfileScreen(
//                            icon = Icons.Outlined.Notifications,
//                            title = "Notifications",
//                            onClick = onNotificationClick
//                        )
//                    }
//                }
//            }
//
//            item {
//                Text(
//                    color = MaterialTheme.colorScheme.primary,
//                    fontSize = 12.sp,
//                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
//                    text = "OTHERS",
//                    letterSpacing = 1.sp,
//                    modifier = Modifier.padding(start = 12.dp, top = 24.dp, bottom = 8.dp)
//                )
//            }
//
//            // Modern rounded grouped card container for Others
//            item {
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(20.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
//                ) {
//                    Column {
//                        EachItemProfileScreen(
//                            icon = Icons.Outlined.Info,
//                            title = "About Us",
//                            onClick = onAboutUsClick
//                        )
//                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
//                        EachItemProfileScreen(
//                            icon = Icons.Outlined.Logout,
//                            title = "Logout",
//                            onClick = onLogoutClick,
//                            isDestructive = true
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EachItemProfileScreen(
//    icon: ImageVector,
//    title: String,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//    isDestructive: Boolean = false
//) {
//    val contentColor = if (isDestructive) {
//        MaterialTheme.colorScheme.error
//    } else {
//        MaterialTheme.colorScheme.onSurface
//    }
//
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(56.dp)
//            .clickable { onClick() }
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = title,
//            tint = contentColor.copy(alpha = 0.8f),
//            modifier = Modifier.size(22.dp)
//        )
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(
//            text = title,
//            fontSize = 14.sp,
//            color = contentColor,
//            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
//            modifier = Modifier.weight(1f)
//        )
//        Icon(
//            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//            contentDescription = "navigate",
//            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//            modifier = Modifier.size(18.dp)
//        )
//    }
//}