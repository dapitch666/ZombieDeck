package org.anne.zombiedeck.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
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
            modifier = modifier
                .conditional(!enabled, { disabled() })
                .size(36.dp)
                .border(3.dp, iconColor, shape = CircleShape),
        ) {
            Icon(
                icon,
                contentDescription = stringResource(id = description),
                tint = iconColor,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview
@Composable
fun DangerLevelIconButtonPreview() {
    DangerLevelIconButton(
        icon = Icons.Outlined.KeyboardArrowLeft,
        color = R.color.danger_blue,
        enabled = true
    )
}

@Preview
@Composable
fun DangerLevelIconButtonDisabledPreview() {
    DangerLevelIconButton(
        icon = Icons.Outlined.KeyboardArrowRight,
        color = R.color.danger_blue,
        enabled = false
    )
}