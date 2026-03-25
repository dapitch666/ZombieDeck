package org.anne.zombiedeck.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val myPreference: MyPreference,
) : ViewModel() {

    private val _state = MutableStateFlow(MyState(showDialog = false))
    val state: StateFlow<MyState>
        get() = _state.asStateFlow()

    val fortHendrix: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean("fortHendrix")
    )

    val dannyTrejo: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean("dannyTrejo")
    )

    private var selectedRanges: Set<IntRange> =
        myPreference.getSelectedCardRanges(fortHendrix.value, dannyTrejo.value)

    private val _easy: MutableStateFlow<Boolean> = MutableStateFlow(
        selectedRanges.contains(rangeForSwitch("easy", fortHendrix.value))
    )
    var easy = _easy.asStateFlow()

    private val _hard: MutableStateFlow<Boolean> = MutableStateFlow(
        selectedRanges.contains(rangeForSwitch("hard", fortHendrix.value))
    )
    var difficult = _hard.asStateFlow()

    private val _extra: MutableStateFlow<Boolean> = MutableStateFlow(
        selectedRanges.contains(rangeForSwitch("extra", fortHendrix.value))
    )
    var extraActivation = _extra.asStateFlow()

    fun toggleSwitch(toggleSettingOption: String){
        when(toggleSettingOption){
            "fortHendrix" -> {
                fortHendrix.value = fortHendrix.value.not()
                myPreference.setBoolean("fortHendrix", fortHendrix.value)
                selectedRanges = remapRangesForFortHendrix(selectedRanges, fortHendrix.value)
                myPreference.setSelectedCardRanges(selectedRanges)
                refreshSwitchStates()
            }
            "dannyTrejo" -> {
                dannyTrejo.value = dannyTrejo.value.not()
                myPreference.setBoolean("dannyTrejo", dannyTrejo.value)
                selectedRanges = selectedRanges.toMutableSet().apply {
                    if (dannyTrejo.value) {
                        add(81..86)
                    } else {
                        remove(81..86)
                    }
                }
                myPreference.setSelectedCardRanges(selectedRanges)
                refreshSwitchStates()
            }
            "easy" -> {
                selectedRanges = toggleRangeSelection(selectedRanges, rangeForSwitch(toggleSettingOption, fortHendrix.value))
                myPreference.setSelectedCardRanges(selectedRanges)
                refreshSwitchStates()
            }
            "hard" -> {
                selectedRanges = toggleRangeSelection(selectedRanges, rangeForSwitch(toggleSettingOption, fortHendrix.value))
                myPreference.setSelectedCardRanges(selectedRanges)
                refreshSwitchStates()
            }
            "extra" -> {
                selectedRanges = toggleRangeSelection(selectedRanges, rangeForSwitch(toggleSettingOption, fortHendrix.value))
                myPreference.setSelectedCardRanges(selectedRanges)
                refreshSwitchStates()
            }
        }
        checkSwitches()
    }

    // this is a helper function to ensure that at least one switch is on
    private fun checkSwitches(){
        val hasAnyDifficultyEnabled =
            selectedRanges.contains(rangeForSwitch("easy", fortHendrix.value)) ||
                selectedRanges.contains(rangeForSwitch("hard", fortHendrix.value)) ||
                selectedRanges.contains(rangeForSwitch("extra", fortHendrix.value))

        if(!hasAnyDifficultyEnabled){
            _state.update { state ->
                state.copy(showDialog = true)
            }
        }
    }

    fun onDismiss(){
        _state.value = _state.value.copy(showDialog = false)
        selectedRanges = selectedRanges.toMutableSet().apply {
            add(rangeForSwitch("easy", fortHendrix.value))
        }
        myPreference.setSelectedCardRanges(selectedRanges)
        refreshSwitchStates()

    }

    private fun refreshSwitchStates() {
        _easy.value = selectedRanges.contains(rangeForSwitch("easy", fortHendrix.value))
        _hard.value = selectedRanges.contains(rangeForSwitch("hard", fortHendrix.value))
        _extra.value = selectedRanges.contains(rangeForSwitch("extra", fortHendrix.value))
    }

    private fun rangeForSwitch(cardsRange: String, fortEnabled: Boolean): IntRange {
        return when (cardsRange) {
            "easy" -> if (fortEnabled) 41..58 else 1..18
            "hard" -> if (fortEnabled) 59..76 else 19..36
            else -> if (fortEnabled) 77..80 else 37..40
        }
    }

    private fun toggleRangeSelection(currentRanges: Set<IntRange>, range: IntRange): Set<IntRange> {
        return currentRanges.toMutableSet().apply {
            if (!remove(range)) {
                add(range)
            }
        }
    }

    private fun remapRangesForFortHendrix(currentRanges: Set<IntRange>, fortEnabled: Boolean): Set<IntRange> {
        return currentRanges.map { range ->
            when (range) {
                1..18 -> if (fortEnabled) 41..58 else range
                19..36 -> if (fortEnabled) 59..76 else range
                37..40 -> if (fortEnabled) 77..80 else range
                41..58 -> if (fortEnabled) range else 1..18
                59..76 -> if (fortEnabled) range else 19..36
                77..80 -> if (fortEnabled) range else 37..40
                else -> range
            }
        }.toSet()

    }
}

data class MyState (
    val showDialog: Boolean
)