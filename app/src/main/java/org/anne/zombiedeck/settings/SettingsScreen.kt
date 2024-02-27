package org.anne.zombiedeck.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.anne.zombiedeck.R
import org.anne.zombiedeck.settings.components.SettingsGroup
import org.anne.zombiedeck.settings.components.SettingsSwitchComp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge
                )
            },
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
                    state = vm.cards1To18.collectAsState()
                ) {
                    // call ViewModel to toggle the value
                    vm.toggleSwitch1To18()
                }
                SettingsSwitchComp(
                    name = R.string.cards_19_to_36,
                    icon = R.drawable.baseline_settings_24,
                    iconDesc = R.string.not_important,
                    // value is collected from StateFlow - updates the UI on change
                    state = vm.cards19To36.collectAsState()
                ) {
                    // call ViewModel to toggle the value
                    vm.toggleSwitch19To36()
                }
                SettingsSwitchComp(
                    name = R.string.cards_37_to_40,
                    icon = R.drawable.baseline_settings_24,
                    iconDesc = R.string.not_important,
                    // value is collected from StateFlow - updates the UI on change
                    state = vm.cards37To40.collectAsState()
                ) {
                    // call ViewModel to toggle the value
                    vm.toggleSwitch37To40()
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
