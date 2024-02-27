package org.anne.zombiedeck.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.anne.zombiedeck.R
import org.anne.zombiedeck.settings.components.SettingsGroup
import org.anne.zombiedeck.settings.components.SettingsSwitchComp

@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val state = vm.state.collectAsState()
    SettingsUIScreen(
        state = state.value,
        cards1To18State = vm.cards1To18.collectAsState(),
        cards19To36State = vm.cards19To36.collectAsState(),
        cards37To40State = vm.cards37To40.collectAsState(),
        toggleSwitch = vm::toggleSwitch,
        onDismiss = vm::onDismiss,
        backButtonOnClick = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUIScreen(
    state: MyState,
    cards1To18State: State<Boolean>,
    cards19To36State: State<Boolean>,
    cards37To40State: State<Boolean>,
    toggleSwitch: (String) -> Unit,
    onDismiss: () -> Unit,
    backButtonOnClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = backButtonOnClick ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {
            SettingsGroup(name = R.string.tuning_the_difficulty) {
                // the switch composable
                SettingsSwitchComp(
                    name = R.string.cards_1_to_18,
                    icon = R.drawable.baseline_settings_24,
                    iconDesc = R.string.not_important,
                    // value is collected from StateFlow - updates the UI on change
                    state = cards1To18State
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("1_to_18")
                }
                SettingsSwitchComp(
                    name = R.string.cards_19_to_36,
                    icon = R.drawable.baseline_settings_24,
                    iconDesc = R.string.not_important,
                    // value is collected from StateFlow - updates the UI on change
                    state = cards19To36State
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("19_to_36")
                }
                SettingsSwitchComp(
                    name = R.string.cards_37_to_40,
                    icon = R.drawable.baseline_settings_24,
                    iconDesc = R.string.not_important,
                    // value is collected from StateFlow - updates the UI on change
                    state = cards37To40State
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("37_to_40")
                }
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(text = stringResource(id = R.string.preferences_alert)) },
            confirmButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(stringResource(id = android.R.string.ok))
                }
            }
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsUIScreen(
        state = MyState(showDialog = false),
        cards1To18State = remember { mutableStateOf(false) },
        cards19To36State = remember { mutableStateOf(true) },
        cards37To40State = remember { mutableStateOf(false) },
        toggleSwitch = {},
        onDismiss = {},
        backButtonOnClick = {}
    )
}
