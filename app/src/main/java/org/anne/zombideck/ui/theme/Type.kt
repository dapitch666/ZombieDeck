package org.anne.zombideck.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import org.anne.zombideck.R

// Using FontVariation.Settings to properly handle the variable font weight axis
@OptIn(ExperimentalTextApi::class)
val oswaldFamily = FontFamily(
    Font(
        R.font.oswald_wght,
        weight = FontWeight.Light,
        variationSettings = FontVariation.Settings(FontVariation.weight(300))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontVariation.weight(400))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(500))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(600))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.ExtraBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(800))
    ),
    Font(
        R.font.oswald_wght,
        weight = FontWeight.Black,
        variationSettings = FontVariation.Settings(FontVariation.weight(900))
    )
)

@OptIn(ExperimentalTextApi::class)
val sonoMonoFont = FontFamily(
    Font(
        R.font.sono_mono_wght,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(700))
    )
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = oswaldFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = oswaldFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = oswaldFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = oswaldFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = oswaldFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = oswaldFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = oswaldFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = oswaldFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = oswaldFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = oswaldFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = oswaldFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = oswaldFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = oswaldFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = oswaldFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = oswaldFamily)
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