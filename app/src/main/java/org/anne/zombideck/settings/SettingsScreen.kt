package org.anne.zombideck.settings

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.anne.zombideck.R
import org.anne.zombideck.data.Expansion
import org.anne.zombideck.settings.components.SettingsGroup
import org.anne.zombideck.settings.components.SettingsSwitchComp

data class ExpansionStates(
    val fortHendrix: Boolean = false,
    val dannyTrejo: Boolean = false,
    val urbanLegends: Boolean = false,
)

data class DifficultyStates(
    val easy: Boolean = true,
    val hard: Boolean = true,
    val extra: Boolean = true,
)

@Composable
fun SettingsScreen(
    vm: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val expansionStates = ExpansionStates(
        fortHendrix  = vm.fortHendrix.collectAsState().value,
        dannyTrejo   = vm.dannyTrejo.collectAsState().value,
        urbanLegends = vm.urbanLegends.collectAsState().value,
    )
    val difficultyStates = DifficultyStates(
        easy  = vm.easy.collectAsState().value,
        hard  = vm.difficult.collectAsState().value,
        extra = vm.extraActivation.collectAsState().value,
    )
    SettingsUIScreen(
        state             = vm.state.collectAsState().value,
        expansionStates   = expansionStates,
        difficultyStates  = difficultyStates,
        toggleSwitch      = vm::toggleSwitch,
        onDismiss         = vm::onDismiss,
        backButtonOnClick = navigateUp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUIScreen(
    state: MyState,
    expansionStates: ExpansionStates,
    difficultyStates: DifficultyStates,
    toggleSwitch: (String) -> Unit,
    onDismiss: () -> Unit,
    backButtonOnClick: () -> Unit,
) {
    val activeExpansion = if (expansionStates.fortHendrix) Expansion.FORT_HENDRIX else Expansion.BASE
    val easyLabel  = stringResource(R.string.cards_range, activeExpansion.easyRange!!.first,  activeExpansion.easyRange.last)
    val hardLabel  = stringResource(R.string.cards_range, activeExpansion.hardRange!!.first,  activeExpansion.hardRange.last)
    val extraLabel = stringResource(R.string.cards_range, activeExpansion.extraRange!!.first, activeExpansion.extraRange.last)

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = {
                IconButton(onClick = backButtonOnClick) {
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
                    testTag = Expansion.FORT_HENDRIX.prefKey,
                    checked = expansionStates.fortHendrix,
                    displaySeparator = false,
                ) { toggleSwitch(Expansion.FORT_HENDRIX.prefKey) }
                SettingsSwitchComp(
                    name = stringResource(R.string.danny_trejo),
                    testTag = Expansion.DANNY_TREJO.prefKey,
                    checked = expansionStates.dannyTrejo,
                    displaySeparator = false,
                ) { toggleSwitch(Expansion.DANNY_TREJO.prefKey) }
                SettingsSwitchComp(
                    name = stringResource(R.string.urban_legends),
                    testTag = Expansion.URBAN_LEGENDS.prefKey,
                    checked = expansionStates.urbanLegends,
                    displaySeparator = false,
                ) { toggleSwitch(Expansion.URBAN_LEGENDS.prefKey) }
            }
            SettingsGroup(name = R.string.tuning_the_difficulty) {
                SettingsSwitchComp(
                    name = easyLabel,
                    summary = R.string.easy_cards_summary,
                    testTag = "easy",
                    checked = difficultyStates.easy,
                ) { toggleSwitch("easy") }
                SettingsSwitchComp(
                    name = hardLabel,
                    summary = R.string.hard_cards_summary,
                    testTag = "hard",
                    checked = difficultyStates.hard,
                ) { toggleSwitch("hard") }
                SettingsSwitchComp(
                    name = extraLabel,
                    summary = R.string.extra_cards_summary,
                    testTag = "extra",
                    checked = difficultyStates.extra,
                    displaySeparator = false,
                ) { toggleSwitch("extra") }
            }
        }
    }

    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(text = stringResource(id = R.string.preferences_alert)) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
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
        state             = MyState(showDialog = false),
        expansionStates   = ExpansionStates(dannyTrejo = true),
        difficultyStates  = DifficultyStates(easy = false, extra = false),
        toggleSwitch      = {},
        onDismiss         = {},
        backButtonOnClick = {},
    )
}

@Preview
@Composable
fun SettingsScreenPreviewDialog() {
    SettingsUIScreen(
        state             = MyState(showDialog = true),
        expansionStates   = ExpansionStates(),
        difficultyStates  = DifficultyStates(easy = false, extra = false),
        toggleSwitch      = {},
        onDismiss         = {},
        backButtonOnClick = {},
    )
}

@Preview
@Composable
fun SettingsScreenFortHendrixPreview() {
    SettingsUIScreen(
        state             = MyState(showDialog = false),
        expansionStates   = ExpansionStates(fortHendrix = true, urbanLegends = true),
        difficultyStates  = DifficultyStates(),
        toggleSwitch      = {},
        onDismiss         = {},
        backButtonOnClick = {},
    )
}