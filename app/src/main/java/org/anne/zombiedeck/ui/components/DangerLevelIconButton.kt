package org.anne.zombiedeck.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombiedeck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DangerLevelIconButton(
    icon: ImageVector,
    @ColorRes color: Int,
    modifier: Modifier = Modifier,
    @StringRes description: Int = R.string.not_important,
    onClick: (isEnabled: Boolean) -> Unit = {},
    enabled: Boolean = true,
) {
    val iconColor = colorResource(color)
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = { onClick(enabled) },
            enabled = enabled,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = iconColor,
                contentColor = Color.White,
                disabledContentColor = Color.White.copy(alpha = 0.9f),
                disabledContainerColor = Color.Gray.copy(alpha = 0.6f)
            ),
            modifier = modifier
                .size(36.dp)
        ) {
            Icon(
                icon,
                contentDescription = stringResource(id = description),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun DangerLevelIconButtonPreview() {
    DangerLevelIconButton(
        icon = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
        color = R.color.danger_blue,
        enabled = true
    )
}

@Preview
@Composable
fun DangerLevelIconButtonDisabledPreview() {
    DangerLevelIconButton(
        icon = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
        color = R.color.danger_blue,
        enabled = false
    )
}