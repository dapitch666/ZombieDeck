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

    private val _cards1To18: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean("cards_1_to_18")
    )
    var cards1To18 = _cards1To18.asStateFlow()

    private val _cards19To36: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean("cards_19_to_36")
    )
    var cards19To36 = _cards19To36.asStateFlow()

    private val _cards37To40: MutableStateFlow<Boolean> = MutableStateFlow(
        myPreference.getBoolean("cards_37_to_40")
    )
    var cards37To40 = _cards37To40.asStateFlow()

    fun toggleSwitch(cardsRange: String){
        when(cardsRange){
            "1_to_18" -> {
                _cards1To18.value = _cards1To18.value.not()
                myPreference.setBoolean("cards_1_to_18", _cards1To18.value)
            }
            "19_to_36" -> {
                _cards19To36.value = _cards19To36.value.not()
                myPreference.setBoolean("cards_19_to_36", _cards19To36.value)
            }
            "37_to_40" -> {
                _cards37To40.value = _cards37To40.value.not()
                myPreference.setBoolean("cards_37_to_40", _cards37To40.value)
            }
        }
        checkSwitches()
    }

    // this is a helper function to ensure that at least one switch is on
    private fun checkSwitches(){
        if(!_cards1To18.value && !_cards19To36.value && !_cards37To40.value){
            //_state.value = _state.value.copy(showDialog = true)
            _state.update { state ->
                state.copy(showDialog = true)
            }
        }
    }

    fun onDismiss(){
        _state.value = _state.value.copy(showDialog = false)
        _cards1To18.value = true
        myPreference.setBoolean("cards_1_to_18", true)

    }
}

data class MyState (
    val showDialog: Boolean
)