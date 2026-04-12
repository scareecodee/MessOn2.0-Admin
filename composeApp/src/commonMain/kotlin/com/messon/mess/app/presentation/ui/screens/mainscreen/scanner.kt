package com.messon.mess.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.messon.mess.app.domain.dataModel.CouponDetail
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.presentation.ui.state.CouponVerificationState
import kotlinx.serialization.json.Json
import messon.composeapp.generated.resources.invalid
import messon.composeapp.generated.resources.valid
import org.jetbrains.compose.resources.painterResource
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.sp


@Suppress("SuspiciousIndentation")
@Composable
fun ScannerScreen(
    onViewDetail:()-> Unit,
    modifier: Modifier,
    couponViewModel: CouponViewModel,
) {
    val Log = Logger.withTag("CMP_TOAST")
    val couponVerificationState by couponViewModel.couponVerificationState.collectAsStateWithLifecycle(
        CouponVerificationState.Idle
    )

    var scannedCouponDetail by remember { mutableStateOf<CouponDetail?>(null) }
    var flashlightOn by remember { mutableStateOf(false) }
    var scannedQrJson by remember { mutableStateOf<String?>(null) }
    var scanEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(couponVerificationState) {
        if (couponVerificationState is CouponVerificationState.Error) {
            showToast((couponVerificationState as CouponVerificationState.Error).message)
        }
    }


    LaunchedEffect(Unit) {
        couponViewModel.clearVerificationError()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        QrScanner(
            modifier = Modifier.fillMaxSize(),
            overlayBorderColor =
                if (scanEnabled) CustomColors.LightBlue
                else CustomColors.HoneyBronze,
            flashlightOn = flashlightOn,
            cameraLens = CameraLens.Back,
            openImagePicker = false,
            overlayShape = OverlayShape.Square,
            onCompletion = {
                if (!scanEnabled) return@QrScanner

                scannedQrJson = it
                scannedCouponDetail = scannedQrJson?.let { json ->
                    try {
                        val parsed = Json.decodeFromString<CouponDetail>(json)
                        when {
                            !parsed.isValid() -> {
                                showToast("Invalid coupon")
                                null
                            }
                            parsed.usedAt != null -> {
                                showToast("Coupon Already Used")
                                null
                            }
                            else -> parsed
                        }
                    } catch (e: Exception) {
                        showToast("Failed to parse QR code")
                        null
                    }
                }

                scannedCouponDetail?.let {
                    couponViewModel.verifyCoupon(scannedCouponDetail!!)
                }

                scanEnabled = false
            },
            onFailure = {
            },
            imagePickerHandler = {},
            customOverlay = null
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = if (scanEnabled) "Scan any coupon QR code" else "Processing scanned QR...",
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            val isStatusVisible = couponVerificationState is CouponVerificationState.Success ||
                    couponVerificationState is CouponVerificationState.Loading ||
                    couponVerificationState is CouponVerificationState.Error

            AnimatedVisibility(
                visible = isStatusVisible,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(16.dp, CircleShape)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (couponVerificationState is CouponVerificationState.Success) {
                        val resp = (couponVerificationState as? CouponVerificationState.Success)?.couponVerificationRes
                        val painterRes = if (resp == null || !resp.isValid) Res.drawable.invalid else Res.drawable.valid
                        Log.d(tag = "coupon verification resp", message = { resp.toString() })
                        Icon(
                            painter = painterResource(painterRes),
                            contentDescription = "Coupon Status",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(55.dp)
                        )
                    }

                    if (couponVerificationState is CouponVerificationState.Loading) {
                        CircularProgressIndicator(
                            color = CustomColors.RichCerulean,
                            modifier = Modifier.size(50.dp),
                            strokeWidth = 4.dp
                        )
                    }

                    if (couponVerificationState is CouponVerificationState.Error) {
                        Icon(
                            painter = painterResource(Res.drawable.invalid),
                            contentDescription = "Invalid Coupon",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 24.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { flashlightOn = !flashlightOn },
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                color = if (flashlightOn) CustomColors.HoneyBronze else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (flashlightOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Torch",
                            tint = if (flashlightOn) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    AnimatedVisibility(visible = scannedQrJson != null) {
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = {
                                    scannedQrJson = null
                                    scanEnabled = true
                                    couponViewModel.clearVerificationError()
                                    couponViewModel.clearCouponDetail()
                                },
                                modifier = Modifier.height(50.dp),
                                shape = RoundedCornerShape(30.dp),
                                border = BorderStroke(1.5.dp, CustomColors.HoneyBronze),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = CustomColors.HoneyBronze
                                )
                            ) {
                                Text(
                                    text = "Scan Again",
                                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick =onViewDetail,
                    enabled = scannedCouponDetail != null &&
                            couponVerificationState !is CouponVerificationState.Error &&
                            couponVerificationState !is CouponVerificationState.Loading      ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .then(
                            if (scannedQrJson != null)
                                Modifier.border(
                                    1.dp,
                                    CustomColors.RichCerulean,
                                    RoundedCornerShape(30.dp)
                                )
                            else Modifier
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.RichCerulean,
                        disabledContainerColor = Color.LightGray.copy(alpha = 0.4f)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    if (couponVerificationState is CouponVerificationState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "View Detail",
                            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                            color = Color.White,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}