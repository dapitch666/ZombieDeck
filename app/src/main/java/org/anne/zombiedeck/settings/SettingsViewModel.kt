package org.anne.zombiedeck.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val myPreference: MyPreference,
) : ViewModel() {

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

    fun toggleSwitch1To18(){
        _cards1To18.value = _cards1To18.value.not()
        myPreference.setBoolean("cards_1_to_18", _cards1To18.value)
        // here is place for permanent storage handling - switch
    }

    fun toggleSwitch19To36(){
        _cards19To36.value = _cards19To36.value.not()
        myPreference.setBoolean("cards_19_to_36", _cards19To36.value)
        // here is place for permanent storage handling - switch
    }

    fun toggleSwitch37To40(){
        _cards37To40.value = _cards37To40.value.not()
        myPreference.setBoolean("cards_37_to_40", _cards37To40.value)
        // here is place for permanent storage handling - switch
    }
}