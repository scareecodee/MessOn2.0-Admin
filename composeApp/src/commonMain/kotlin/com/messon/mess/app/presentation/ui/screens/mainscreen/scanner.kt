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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
import com.messon.mess.app.presentation.ui.state.MarkCouponAsUsedState
import kotlinx.serialization.json.Json
import messon.composeapp.generated.resources.invalid
import messon.composeapp.generated.resources.valid
import org.jetbrains.compose.resources.painterResource

@Suppress("SuspiciousIndentation")
@Composable
fun ScannerScreen(
    modifier: Modifier,
    couponViewModel: CouponViewModel,
) {
    val Log= Logger.withTag("CMP_TOAST")
    val couponVerificationState by couponViewModel.couponVerificationState.collectAsStateWithLifecycle(
        CouponVerificationState.Idle)

    val markCouponAsUsedState by couponViewModel.markCouponAsUsed.collectAsStateWithLifecycle(
        MarkCouponAsUsedState.Idle)

    var scannedCouponDetail by remember { mutableStateOf<CouponDetail?>(null) }
    var flashlightOn by remember { mutableStateOf(false) }
    var scannedQrJson by remember { mutableStateOf<String?>(null) }
    var scanEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(couponVerificationState) {
        if (couponVerificationState is CouponVerificationState.Error) {
            showToast((couponVerificationState as CouponVerificationState.Error).message)
            couponViewModel.clearVerificationError()
        }

    }

    LaunchedEffect(markCouponAsUsedState) {
        when (markCouponAsUsedState) {
            is MarkCouponAsUsedState.Error -> {
                showToast((markCouponAsUsedState as MarkCouponAsUsedState.Error).message)
                couponViewModel.clearMarkAsUsedError()
            }
            is MarkCouponAsUsedState.Success -> {
                showToast("Coupon marked as used")
                couponViewModel.clearMarkAsUsedError()
            }
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        couponViewModel.clearVerificationError()
    }


    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {

        val (camera, torch, scanAgainBtn, actionButton,stateRef) = createRefs()


        QrScanner(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(camera) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
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
                showToast(message = it)
            },
            onFailure = {
            },
            imagePickerHandler = {},
            customOverlay = null
        )

        IconButton(
            onClick = { flashlightOn = !flashlightOn },
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = CustomColors.HoneyBronze,
                    shape = CircleShape
                )
                .constrainAs(torch) {
                    bottom.linkTo(actionButton.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector =
                    if (flashlightOn) Icons.Default.FlashOn
                    else Icons.Default.FlashOff,
                contentDescription = "Torch",
                tint = Color.White
            )
        }

        AnimatedVisibility(
            visible = scannedQrJson != null,
            modifier = Modifier.constrainAs(scanAgainBtn) {
                bottom.linkTo(torch.top, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            OutlinedButton(
                onClick = {
                    scannedQrJson = null
                    scanEnabled = true
                    couponViewModel.clearVerificationError()
                    couponViewModel.clearMarkAsUsedError()
                },
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(1.dp, CustomColors.HoneyBronze),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = CustomColors.HoneyBronze
                )
            ) {
                Text(
                    text = "Scan Again",
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold))
                )
            }
        }

        Button(
            onClick = {
                scannedCouponDetail?.let {
                    couponViewModel.markCouponAsUsed(it)
                }
            },
            enabled = scannedCouponDetail != null,
            modifier = Modifier
                .height(50.dp)
                .then(
                    if (scannedQrJson!= null)
                        Modifier.border(
                            1.dp,
                            CustomColors.RichCerulean,
                            RoundedCornerShape(30.dp)
                        )
                    else Modifier
                )
                .constrainAs(actionButton) {
                    bottom.linkTo(parent.bottom, margin = 30.dp)
                    start.linkTo(parent.start, margin = 50.dp)
                    end.linkTo(parent.end, margin = 50.dp)
                    width = Dimension.fillToConstraints
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = CustomColors.RichCerulean.copy(alpha = 0.3f),
                disabledContainerColor = Color.LightGray.copy(alpha = 0.4f)
            ),
            shape = RoundedCornerShape(30.dp)
        ) {
            if (markCouponAsUsedState is MarkCouponAsUsedState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text(
                    text = "Mark as used",
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(stateRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = couponVerificationState is CouponVerificationState.Success) {
                val resp = (couponVerificationState as? CouponVerificationState.Success)?.couponVerificationRes
                val painterRes = if (resp==null || !resp.isValid) Res.drawable.invalid else Res.drawable.valid
                    Log.d(tag = "coupon veri resp", message = {resp.toString()})
                Icon(
                    painter = painterResource(painterRes),
                    contentDescription = "Coupon Status",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
            }


            AnimatedVisibility(visible = couponVerificationState is CouponVerificationState.Loading) {
              CircularProgressIndicator(
                  color = CustomColors.RichCerulean
              )

            }


            AnimatedVisibility(visible = couponVerificationState is CouponVerificationState.Error) {
                Icon(
                    painter = painterResource(Res.drawable.invalid),
                    contentDescription = "Invalid Coupon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }

    }
}
