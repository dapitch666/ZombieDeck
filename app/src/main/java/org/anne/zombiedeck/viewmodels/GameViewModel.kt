package org.anne.zombiedeck.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.anne.zombiedeck.data.Abomination
import org.anne.zombiedeck.data.DeckState
import org.anne.zombiedeck.data.allCards

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DeckState())
    val uiState: StateFlow<DeckState> = _uiState.asStateFlow()

    private var deck = allCards.shuffled()
    val abominations = Abomination.entries

    fun nextCard() {
        var currentCardIndex = _uiState.value.currentCardIndex
        // If we are at the last card, shuffle the deck and start over
        if (isLastCard()) {
            deck = allCards.shuffled()
            currentCardIndex = -1
        }
        val nextCardIndex = currentCardIndex + 1
        val nextCard = deck[nextCardIndex]
        _uiState.value = _uiState.value.copy(
            currentCardIndex = nextCardIndex,
            currentCard = nextCard,
            abominationJustDrawn = false,
        )
    }

    fun drawNewAbomination() {
        val currentAbomination = abominations.random()
        _uiState.value = _uiState.value.copy(
            currentAbomination = currentAbomination,
            abominationJustDrawn = true
        )
    }

    fun previousCard() {
        val currentCardIndex = _uiState.value.currentCardIndex
        val previousCardIndex = currentCardIndex - 1
        val previousCard = deck[previousCardIndex]
        _uiState.value = _uiState.value.copy(
            currentCardIndex = previousCardIndex,
            currentCard = previousCard,
            abominationJustDrawn = false,
        )
    }

    fun getProgress(): Float {
        return (_uiState.value.currentCardIndex.toFloat() + 1) / deck.size
    }

    fun isLastCard(): Boolean {
        return _uiState.value.currentCardIndex == deck.size - 1
    }

    fun isFirstCard(): Boolean {
        return _uiState.value.currentCardIndex <= 0
    }

    fun increaseDangerLevel() {
        _uiState.value = _uiState.value.copy(currentDanger = _uiState.value.currentDanger.next())
    }

    fun decreaseDangerLevel() {
        _uiState.value = _uiState.value.copy(currentDanger = _uiState.value.currentDanger.previous())
    }
}
