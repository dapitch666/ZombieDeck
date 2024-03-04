package org.anne.zombiedeck.settings.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombiedeck.R

@Composable
fun SettingsSwitchComp(
    @DrawableRes icon: Int? = null,
    @StringRes iconDesc: Int? = null,
    @StringRes name: Int,
    @StringRes summary: Int? = null,
    state: State<Boolean>,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (icon != null && iconDesc != null) {
                            Icon(
                                painterResource(id = icon),
                                contentDescription = stringResource(id = iconDesc),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = stringResource(id = name),
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                        )
                    }
                    if (summary != null) {
                        Text(
                            text = stringResource(id = summary),
                            modifier = Modifier
                                .width(250.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = state.value,
                    onCheckedChange = { onClick() }
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun SettingsSwitchCompWithIconPreview() {
    SettingsSwitchComp(
        icon = android.R.drawable.ic_menu_camera,
        iconDesc = android.R.string.ok,
        name = android.R.string.cancel,
        state = remember {
            mutableStateOf(true)
        },
        onClick = {}
    )
}

@Preview
@Composable
fun SettingsSwitchCompPreview() {
    SettingsSwitchComp(
        name = R.string.cards_1_to_18,
        summary = R.string.cards_1_to_18_summary,
        state = remember {
            mutableStateOf(true)
        },
        onClick = {}
    )
}

