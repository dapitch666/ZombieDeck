package org.anne.zombideck.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.anne.zombideck.data.Expansion
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val myPreference: MyPreference,
) : ViewModel() {

    private val _state = MutableStateFlow(MyState(showDialog = false))
    val state: StateFlow<MyState>
        get() = _state.asStateFlow()

    // ── Expansion toggles ────────────────────────────────────────────────────────

    val fortHendrix: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean(Expansion.FORT_HENDRIX.prefKey, defValue = false)
    )

    val dannyTrejo: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean(Expansion.DANNY_TREJO.prefKey, defValue = false)
    )

    val urbanLegends: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean(Expansion.URBAN_LEGENDS.prefKey, defValue = false)
    )

    // ── Difficulty sub-range toggles ─────────────────────────────────────────────
    //
    // "easy / hard / extra" only make sense when an expansion with difficulty
    // ranges is active (BASE or FORT_HENDRIX). The active expansion is whichever
    // one is currently selected; when Fort Hendrix is ON, Fort Hendrix ranges are
    // used, otherwise BASE ranges are used.

    private val activeExpansion: Expansion
        get() = if (fortHendrix.value) Expansion.FORT_HENDRIX else Expansion.BASE

    private var selectedRanges: Set<IntRange> =
        myPreference.getSelectedCardRanges(enabledExpansions())

    private val _easy = MutableStateFlow(selectedRanges.contains(activeExpansion.easyRange))
    val easy: StateFlow<Boolean> = _easy.asStateFlow()

    private val _hard = MutableStateFlow(selectedRanges.contains(activeExpansion.hardRange))
    val difficult: StateFlow<Boolean> = _hard.asStateFlow()

    private val _extra = MutableStateFlow(selectedRanges.contains(activeExpansion.extraRange))
    val extraActivation: StateFlow<Boolean> = _extra.asStateFlow()

    // ── Public API ───────────────────────────────────────────────────────────────

    fun toggleSwitch(toggleSettingOption: String) {
        when (toggleSettingOption) {
            Expansion.FORT_HENDRIX.prefKey -> toggleFortHendrix()
            Expansion.DANNY_TREJO.prefKey  -> toggleDannyTrejo()
            Expansion.URBAN_LEGENDS.prefKey -> toggleUrbanLegends()
            "easy"  -> toggleDifficultyRange { activeExpansion.easyRange }
            "hard"  -> toggleDifficultyRange { activeExpansion.hardRange }
            "extra" -> toggleDifficultyRange { activeExpansion.extraRange }
        }
        checkSwitches()
    }

    fun onDismiss() {
        _state.value = _state.value.copy(showDialog = false)
        // Re-enable "easy" as a safety fallback so the deck is never empty.
        activeExpansion.easyRange?.let { easyRange ->
            selectedRanges = selectedRanges.toMutableSet().apply { add(easyRange) }
            myPreference.setSelectedCardRanges(selectedRanges)
            refreshSwitchStates()
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────────

    private fun toggleFortHendrix() {
        fortHendrix.value = !fortHendrix.value
        myPreference.setBoolean(Expansion.FORT_HENDRIX.prefKey, fortHendrix.value)
        // Remap difficulty ranges to the newly active expansion.
        selectedRanges = remapDifficultyRanges(selectedRanges, fortHendrix.value)
        myPreference.setSelectedCardRanges(selectedRanges)
        refreshSwitchStates()
    }

    private fun toggleDannyTrejo() {
        dannyTrejo.value = !dannyTrejo.value
        myPreference.setBoolean(Expansion.DANNY_TREJO.prefKey, dannyTrejo.value)
        val trejoRange = Expansion.DANNY_TREJO.cardRange ?: return
        selectedRanges = selectedRanges.toMutableSet().apply {
            if (dannyTrejo.value) add(trejoRange) else remove(trejoRange)
        }
        myPreference.setSelectedCardRanges(selectedRanges)
        refreshSwitchStates()
    }

    private fun toggleUrbanLegends() {
        urbanLegends.value = !urbanLegends.value
        myPreference.setBoolean(Expansion.URBAN_LEGENDS.prefKey, urbanLegends.value)
        // Urban Legends has no cards, so no selectedRanges update needed.
    }

    private fun toggleDifficultyRange(rangeProvider: () -> IntRange?) {
        val range = rangeProvider() ?: return
        selectedRanges = selectedRanges.toMutableSet().apply {
            if (!remove(range)) add(range)
        }
        myPreference.setSelectedCardRanges(selectedRanges)
        refreshSwitchStates()
    }

    private fun refreshSwitchStates() {
        _easy.value  = selectedRanges.contains(activeExpansion.easyRange)
        _hard.value  = selectedRanges.contains(activeExpansion.hardRange)
        _extra.value = selectedRanges.contains(activeExpansion.extraRange)
    }

    private fun enabledExpansions(): Set<Expansion> =
        Expansion.entries.filterTo(mutableSetOf()) { expansion ->
            expansion == Expansion.BASE ||
                    myPreference.getBoolean(expansion.prefKey, defValue = false)
        }

    /**
     * Remaps difficulty sub-ranges when the Fort Hendrix expansion is toggled.
     * BASE sub-ranges (1–18, 19–36, 37–40) ↔ FORT_HENDRIX sub-ranges (41–58, 59–76, 77–80).
     */
    private fun remapDifficultyRanges(current: Set<IntRange>, fortEnabled: Boolean): Set<IntRange> {
        val from = if (fortEnabled) Expansion.BASE else Expansion.FORT_HENDRIX
        val to   = if (fortEnabled) Expansion.FORT_HENDRIX else Expansion.BASE

        return current.map { range ->
            when (range) {
                from.easyRange  -> to.easyRange  ?: range
                from.hardRange  -> to.hardRange  ?: range
                from.extraRange -> to.extraRange ?: range
                else            -> range
            }
        }.toSet()
    }

    /** Shows a dialog if the player disabled all difficulty ranges (deck would be empty). */
    private fun checkSwitches() {
        val exp = activeExpansion
        val hasAnyEnabled =
            selectedRanges.contains(exp.easyRange) ||
                    selectedRanges.contains(exp.hardRange) ||
                    selectedRanges.contains(exp.extraRange)

        if (!hasAnyEnabled) {
            _state.update { it.copy(showDialog = true) }
        }
    }
}

data class MyState(
    val showDialog: Boolean,
)