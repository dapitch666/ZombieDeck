package org.anne.zombiedeck.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
        easyState = vm.easy.collectAsState(),
        hardState = vm.difficult.collectAsState(),
        extraState = vm.extraActivation.collectAsState(),
        fortHendrixState = vm.fortHendrix.collectAsState(),
        dannyTrejoState = vm.dannyTrejo.collectAsState(),
        urbanLegendsState = vm.urbanLegends.collectAsState(),
        toggleSwitch = vm::toggleSwitch,
        onDismiss = vm::onDismiss,
        backButtonOnClick = navigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUIScreen(
    state: MyState,
    easyState: State<Boolean>,
    hardState: State<Boolean>,
    extraState: State<Boolean>,
    fortHendrixState: State<Boolean>,
    dannyTrejoState: State<Boolean>,
    urbanLegendsState: State<Boolean>,
    toggleSwitch: (String) -> Unit,
    onDismiss: () -> Unit,
    backButtonOnClick: () -> Unit
) {
    val cardRanges = if (fortHendrixState.value) {
        listOf(41 to 58, 59 to 76, 77 to 80)
    } else {
        listOf(1 to 18, 19 to 36, 37 to 40)
    }
    val easyLabel = stringResource(R.string.cards_range, cardRanges[0].first, cardRanges[0].second)
    val hardLabel = stringResource(R.string.cards_range, cardRanges[1].first, cardRanges[1].second)
    val extraLabel = stringResource(R.string.cards_range, cardRanges[2].first, cardRanges[2].second)

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
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back))
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
                .padding(13.dp)
        ) {
            SettingsGroup(name = R.string.expansions) {
                SettingsSwitchComp(
                    name = stringResource(R.string.fort_hendrix),
                    testTag = "fort_hendrix",
                    state = fortHendrixState,
                    displaySeparator = false
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("fortHendrix")
                }
                SettingsSwitchComp(
                    name = stringResource(R.string.danny_trejo),
                    testTag = "danny_trejo",
                    state = dannyTrejoState,
                    displaySeparator = false
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("dannyTrejo")
                }
                SettingsSwitchComp(
                    name = stringResource(R.string.urban_legends),
                    testTag = "urban_legends",
                    state = urbanLegendsState,
                    displaySeparator = false
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("urbanLegends")
                }
            }
            SettingsGroup(name = R.string.tuning_the_difficulty) {
                // the switch composable
                SettingsSwitchComp(
                    name = easyLabel,
                    summary = R.string.easy_cards_summary,
                    testTag = "easy",
                    state = easyState
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("easy")
                }
                SettingsSwitchComp(
                    name = hardLabel,
                    summary = R.string.hard_cards_summary,
                    testTag = "hard",
                    state = hardState
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("hard")
                }
                SettingsSwitchComp(
                    name = extraLabel,
                    summary = R.string.extra_cards_summary,
                    testTag = "extra",
                    state = extraState,
                    displaySeparator = false
                ) {
                    // call ViewModel to toggle the value
                    toggleSwitch("extra")
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

@Preview(device = "id:small_phone", showSystemUi = true)
@Preview(device = "id:medium_phone", showSystemUi = true)
@Preview(locale = "fr", device = "spec:width=384dp,height=832dp,dpi=450", showSystemUi = true, name = "Galaxy A56 French")
@Composable
fun SettingsScreenPreview() {
    SettingsUIScreen(
        state = MyState(showDialog = false),
        easyState = remember { mutableStateOf(false) },
        hardState = remember { mutableStateOf(true) },
        extraState = remember { mutableStateOf(false) },
        fortHendrixState = remember { mutableStateOf(false) },
        dannyTrejoState = remember { mutableStateOf(true) },
        urbanLegendsState = remember { mutableStateOf(false) },
        toggleSwitch = {},
        onDismiss = {},
        backButtonOnClick = {},
    )
}

@Preview
@Composable
fun SettingsScreenPreviewDialog() {
    SettingsUIScreen(
        state = MyState(showDialog = true),
        easyState = remember { mutableStateOf(false) },
        hardState = remember { mutableStateOf(true) },
        extraState = remember { mutableStateOf(false) },
        fortHendrixState = remember { mutableStateOf(false) },
        dannyTrejoState = remember { mutableStateOf(false) },
        urbanLegendsState = remember { mutableStateOf(false) },
        toggleSwitch = {},
        onDismiss = {},
        backButtonOnClick = {},
    )
}

@Preview
@Composable
fun SettingsScreenFortHendrixPreview() {
    SettingsUIScreen(
        state = MyState(showDialog = false),
        easyState = remember { mutableStateOf(true) },
        hardState = remember { mutableStateOf(true) },
        extraState = remember { mutableStateOf(true) },
        fortHendrixState = remember { mutableStateOf(true) },
        dannyTrejoState = remember { mutableStateOf(false) },
        urbanLegendsState = remember { mutableStateOf(true) },
        toggleSwitch = {},
        onDismiss = {},
        backButtonOnClick = {},
    )
}