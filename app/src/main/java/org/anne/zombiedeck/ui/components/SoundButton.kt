package org.anne.zombiedeck.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombiedeck.R

@Composable
fun SoundButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
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
    IconButton(
        onClick = { onClick() },
        colors = colors,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.toggle_sound_on_off),
            modifier = Modifier
                .size(32.dp)
        )
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