package com.messon.mess.app.presentation.ui.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.ui.state.AuthState
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.google
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.ToastPadding
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
    isCircularProgressVisible: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (statusBarBox, illustrationRef, headingRef, subtitleRef, googleBtn, textRef, circularProgressOverlay) = createRefs()

        // Adaptive top status bar spacer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(80.dp)
                )
                .constrainAs(illustrationRef) {
                    top.linkTo(statusBarBox.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "🍱",
                fontSize = 56.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Login to MessOn",
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(headingRef) {
                    top.linkTo(illustrationRef.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "Your daily campus mess companion",
            fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant, // High legibility secondary color
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(subtitleRef) {
                    top.linkTo(headingRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Modernized elevated Google Button
        Button(
            onClick = { onBtnClick() },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface, // Clean white in Light Mode, Slate Charcoal in Dark Mode
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp
            ),
            modifier = Modifier
                .constrainAs(googleBtn) {
                    top.linkTo(subtitleRef.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), // Perfect low-contrast outline matching system theme
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.google),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Continue with Google",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface // Responsive un-hardcoded text color
            )
        }

        // Beautifully balanced disclaimer block
        Text(
            text = "By continuing, you agree to our User Agreement and acknowledge that you understand the Privacy Policy",
            fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), // High readability fallback
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textRef) {
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 40.dp)
        )

        if (isCircularProgressVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f)) // Dim screen overlay
                    .clickable(enabled = false) {} // Absorb pointer clicks cleanly
                    .constrainAs(circularProgressOverlay) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.size(90.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            strokeCap = StrokeCap.Round,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SignInRoute(
    googleAuth: GoogleAuth,
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.value

    isVisible = state is AuthState.Loading

    SignInScreen(
        onBtnClick = {
            viewModel.onGoogleClick(googleAuth = googleAuth)
        },
        isCircularProgressVisible = isVisible
    )

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            onLoginSuccess()
        }
        if (state is AuthState.Error) {
            showToast(
                textSize = 14.sp, // Modern balanced text size for toasts
                message = state.message,
                backgroundColor = Color.DarkGray, // Adaptive styling for errors
                textColor = Color.White,
                gravity = ToastGravity.Top,
                padding = ToastPadding(12),
            )
        }
    }
}