package com.example.azbuka.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.azbuka.R

val SfProText = FontFamily(
    Font(R.font.sf_pro_text_light, FontWeight.Light),
    Font(R.font.sf_pro_text_regular, FontWeight.Normal),
    Font(R.font.sf_pro_text_medium, FontWeight.Medium),
    Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold),
    Font(R.font.sf_pro_text_bold, FontWeight.Bold),
    Font(R.font.sf_pro_text_heavy, FontWeight.ExtraBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Bold,
        fontSize = 120.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
)

val ResetButtonFontSize = 16.sp

val Typography.ButtonText: TextStyle
    get() = TextStyle(
        fontFamily = SfProText,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

val Typography.CaptionText: TextStyle
    get() = TextStyle(
        fontFamily = SfProText,
        fontSize = 14.sp
    )

val AlphabetLetterFontWeight = FontWeight.Bold
val TitleFontWeight = FontWeight.SemiBold

val FinalCounterTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)

val FinalNavTitleTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)

val FinalNavSubtitleTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)

val FinalNavProgressTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp
)

val CounterTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

val WelcomeTitleTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp
)

val WelcomeAppNameTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp
)

val WelcomeButtonTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)

val WelcomeLoginPromptTextStyle = TextStyle(
    fontFamily = SfProText,
    fontSize = 16.sp
)


val LogoutText22 = 22.sp
val Title22 = 22.sp
val Title20 = 20.sp
val Label12 = 12.sp

object Dimens {
    val Body16 = 16.sp
}

val StudyDayLabelTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = StudyDayDayLabelFontSize
)

val WeeklySummaryPercentageTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.Medium,
    fontSize = WeeklySummaryPercentageFontSize,
    color = TextDark
)

val WeeklySummaryDayLabelTextStyle = TextStyle(
    fontFamily = SfProText,
    fontWeight = FontWeight.SemiBold,
    fontSize = WeeklySummaryLabelFontSize,
    color = TextDark
)

val FontWeightBold = FontWeight.Bold

val RatingNavWeeklySummaryTitleTextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = Title20
)

val RatingNavBodyBoldTextStyle = TextStyle(
    fontWeight = FontWeightBold,
    fontSize = Body16
)

val AppTitleTextStyle = TextStyle(
    fontSize = 26.sp,
    fontWeight = FontWeight.Bold,
    color = PrimaryTextColor
)

val PasswordRuleTextStyle = TextStyle(
    fontSize = 11.sp,
    fontFamily = SfProText,
    fontWeight = FontWeight.Light,
    color = PasswordRuleTextColor
)