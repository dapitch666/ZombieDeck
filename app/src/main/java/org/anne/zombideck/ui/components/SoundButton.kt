package org.anne.zombideck.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombideck.R

@Composable
fun SoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    // `enabled` represents the current sound state shown by icon + color.
    val icon = if (enabled) R.drawable.sound_on else R.drawable.sound_off
    val colors = if (enabled) {
        IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = R.color.danger_yellow),
            contentColor = Color.Black
        )
    } else {
        IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = R.color.danger_blue),
            contentColor = Color.White
        )
    }

    // Keep icon-only interaction compact while preserving clear state feedback.
    Surface(
        shape = CircleShape,
        shadowElevation = 6.dp,
        color = Color.Transparent,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(36.dp)
    ) {
        IconButton(
            onClick = { onClick() },
            colors = colors,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.toggle_sound_on_off),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun SoundButtonPreview() {
    Row {
        SoundButton(enabled = true)
        SoundButton(enabled = false)
    }
}