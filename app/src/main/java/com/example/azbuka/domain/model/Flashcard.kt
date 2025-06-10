package com.example.azbuka.domain.model

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.example.azbuka.R
import com.example.azbuka.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.ui.tooling.preview.Preview
import com.example.azbuka.ui.theme.RightSwipeColor
import com.example.azbuka.ui.theme.LeftSwipeColor
import com.example.azbuka.ui.theme.CardBackgroundColor
import com.example.azbuka.ui.theme.CardTextColor
import com.example.azbuka.ui.theme.Typography
import androidx.compose.animation.core.LinearEasing


@Composable
fun Flashcard(
    modifier: Modifier = Modifier,
    frontText: String,
    backText: String = "",
    frontImage: Painter? = null,
    backImage: Painter? = null,
    height: Dp = CARD_HEIGHT,
    width: Dp = CARD_WIDTH,
    borderStrokeWidth: Dp = BORDER_STROKE_WIDTH,
    rightSwipeColor: Color = RightSwipeColor,
    leftSwipeColor: Color = LeftSwipeColor,
    backgroundColor: Color = CardBackgroundColor,
    textColor: Color = CardTextColor,
    shape: RoundedCornerShape = RoundedCornerShape(CARD_CORNER_RADIUS),
    topButtonRow: @Composable (() -> Unit)? = null,
    flipDuration: Int = FLIP_ANIMATION_DURATION,
    alphaDuration: Int = ALPHA_ANIMATION_DURATION,
    swipeDuration: Int = SWIPE_ANIMATION_TIME,
    swipeThreshold: Float = SWIPE_GESTURE_THRESHOLD,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onRightSwipeApproach: () -> Unit = {},
    onLeftSwipeApproach: () -> Unit = {},
    onNeutralPosition: () -> Unit = {}
) {
    val navWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }


    val scope = rememberCoroutineScope()
    val isCardFlipped = remember { mutableStateOf(false) }
    val cardBorderColor = remember { mutableStateOf(Color.Transparent) }
    val cardRotationY = remember { Animatable(0f) }
    val cardOffsetX = remember { Animatable(0f) }
    val cardOffsetY = remember { Animatable(0f) }

    val cardSidesAlpha = animateFloatAsState(
        targetValue = if (!isCardFlipped.value) 1f else 0f,
        animationSpec = tween(durationMillis = alphaDuration, easing = LinearOutSlowInEasing),
        label = "CardAlpha"
    )

    Box(modifier = Modifier.width(width).height(height)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(backgroundColor)
        )

        Box(
            modifier = modifier
                .offset { IntOffset(cardOffsetX.value.roundToInt(), cardOffsetY.value.roundToInt()) }
                .graphicsLayer { rotationZ = cardOffsetX.value / 100 }
                .fillMaxSize()
                .border(BorderStroke(borderStrokeWidth, cardBorderColor.value), shape = shape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            handleSwipeGestureCompletion(
                                scope, cardOffsetX, cardOffsetY, cardRotationY,
                                { isCardFlipped.value = it }, { cardBorderColor.value = it },
                                onSwipeLeft, onSwipeRight, swipeThreshold, navWidth, swipeDuration
                            )
                        },
                        onDragCancel = {
                            handleSwipeGestureCompletion(
                                scope, cardOffsetX, cardOffsetY, cardRotationY,
                                { isCardFlipped.value = it }, { cardBorderColor.value = it },
                                onSwipeLeft, onSwipeRight, swipeThreshold, navWidth, swipeDuration
                            )
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                cardOffsetX.snapTo(cardOffsetX.value + dragAmount.x)
                                cardOffsetY.snapTo(cardOffsetY.value + dragAmount.y)
                            }
                            when {
                                cardOffsetX.value > swipeThreshold -> {
                                    cardBorderColor.value = rightSwipeColor
                                    onRightSwipeApproach()
                                }
                                cardOffsetX.value < -swipeThreshold -> {
                                    cardBorderColor.value = leftSwipeColor
                                    onLeftSwipeApproach()
                                }
                                else -> {
                                    cardBorderColor.value = Color.Transparent
                                    onNeutralPosition()
                                }
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        isCardFlipped.value = !isCardFlipped.value
                        scope.launch {
                            cardRotationY.animateTo(
                                targetValue = if (!isCardFlipped.value) 0f else 180f,
                                animationSpec = tween(flipDuration, easing = LinearOutSlowInEasing)
                            )
                        }
                    }
                }
        ) {
            FlashcardSide(
                text = frontText,
                image = frontImage,
                rotationY = cardRotationY.value,
                alpha = cardSidesAlpha.value,
                shape = shape,
                topButtonRow = topButtonRow,
                backgroundColor = backgroundColor,
                textColor = textColor
            )

            FlashcardSide(
                text = backText,
                image = backImage,
                rotationY = cardRotationY.value,
                scaleX = if (cardRotationY.value >= 90f) -1f else 1f,
                alpha = 1f - cardSidesAlpha.value,
                shape = shape,
                topButtonRow = topButtonRow,
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

@Composable
fun FlashcardSide(
    text: String,
    image: Painter? = null,
    rotationY: Float,
    scaleX: Float = 1f,
    alpha: Float,
    shape: RoundedCornerShape,
    backgroundColor: Color,
    textColor: Color,
    topButtonRow: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                transformOrigin = TransformOrigin.Center
                this.rotationY = rotationY
                this.scaleX = scaleX
                cameraDistance = CARD_CAMERA_DISTANCE
            }
            .alpha(alpha)
            .clip(shape)
            .background(backgroundColor)
            .padding(CARD_PADDING),
        contentAlignment = Alignment.Center
    ) {
        topButtonRow?.invoke()
        if (image != null) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(IMAGE_SIZE)
            )
        } else {
            Text(
                text = text,
                fontSize = Typography.displayLarge.fontSize,
                fontWeight = Typography.displayLarge.fontWeight ?: FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = textColor
            )

        }
    }
}

private fun handleSwipeGestureCompletion(
    scope: CoroutineScope,
    animatedCardOffsetX: Animatable<Float, AnimationVector1D>,
    animatedCardOffsetY: Animatable<Float, AnimationVector1D>,
    cardRotationY: Animatable<Float, AnimationVector1D>,
    updateIsCardFlipped: (Boolean) -> Unit,
    updateCardBorderColor: (Color) -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    swipeThreshold: Float,
    navWidth: Float,
    swipeDuration: Int
) {
    scope.launch {
        when {
            animatedCardOffsetX.value > swipeThreshold -> {
                updateIsCardFlipped(false)
                launch { cardRotationY.snapTo(0f) }
                animatedCardOffsetX.animateTo(
                    targetValue = navWidth * 1.5f,
                    animationSpec = tween(durationMillis = swipeDuration, easing = LinearEasing)
                )
                onSwipeRight()
                delay(AFTER_SWIPE_DELAY)
                resetCardPositionAndColor(animatedCardOffsetX, animatedCardOffsetY, updateCardBorderColor)
            }
            animatedCardOffsetX.value < -swipeThreshold -> {
                updateIsCardFlipped(false)
                launch { cardRotationY.snapTo(0f) }
                animatedCardOffsetX.animateTo(
                    targetValue = -navWidth * 1.5f,
                    animationSpec = tween(durationMillis = swipeDuration, easing = LinearEasing)
                )
                onSwipeLeft()
                delay(AFTER_SWIPE_DELAY)
                resetCardPositionAndColor(animatedCardOffsetX, animatedCardOffsetY, updateCardBorderColor)
            }
            else -> {
                launch { animatedCardOffsetX.animateTo(0f) }
                launch { animatedCardOffsetY.animateTo(0f) }
            }
        }
    }
}

private suspend fun resetCardPositionAndColor(
    animatedCardOffsetX: Animatable<Float, AnimationVector1D>,
    animatedCardOffsetY: Animatable<Float, AnimationVector1D>,
    changeCardBorderColor: (Color) -> Unit
) {
    animatedCardOffsetX.snapTo(0f)
    animatedCardOffsetY.snapTo(0f)
    changeCardBorderColor(Color.Transparent)
}

@Preview
@Composable
private fun FlashcardPreview() {
    Flashcard(
        frontText = "А",
        backImage = painterResource(id = R.drawable.lettera)
    )
}