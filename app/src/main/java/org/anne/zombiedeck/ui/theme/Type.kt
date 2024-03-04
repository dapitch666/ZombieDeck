package org.anne.zombiedeck.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.anne.zombiedeck.R

val overpassFamily = FontFamily(
    Font(R.font.overpass_light, FontWeight.Light),
    Font(R.font.overpass_regular, FontWeight.Normal),
    Font(R.font.overpass_medium, FontWeight.Medium),
    Font(R.font.overpass_semibold, FontWeight.SemiBold),
    Font(R.font.overpass_bold, FontWeight.ExtraBold),
    Font(R.font.overpass_black, FontWeight.Black)
)

val overpassMonoFont = FontFamily(
    Font(R.font.overpass_mono_bold, FontWeight.Bold)
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = overpassFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = overpassFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = overpassFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = overpassFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = overpassFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = overpassFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = overpassFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = overpassFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = overpassFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = overpassFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = overpassFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = overpassFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = overpassFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = overpassFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = overpassFamily)

)

// Set of Material typography styles to start with
/*
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)*/