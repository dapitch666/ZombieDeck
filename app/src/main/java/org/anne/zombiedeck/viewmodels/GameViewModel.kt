package org.anne.zombiedeck.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.anne.zombiedeck.data.AbominationRepository
import org.anne.zombiedeck.data.Card
import org.anne.zombiedeck.data.DeckState
import org.anne.zombiedeck.data.allCards
import org.anne.zombiedeck.settings.MyPreference
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val myPreference: MyPreference?) : ViewModel() {
    private val _uiState = MutableStateFlow(DeckState())
    val uiState: StateFlow<DeckState> = _uiState.asStateFlow()

    private val _isMuted = MutableStateFlow(myPreference?.getBoolean("isMuted") ?: true)
    val isMuted = _isMuted.asStateFlow()

    private lateinit var deck: List<Card>
    private var isForward = true
    private val abominationRepository = AbominationRepository(myPreference)

    init {
        resetDeck()
    }

    private fun resetDeck() {
        val fortHendrixEnabled = myPreference?.getBoolean("fortHendrix") ?: false
        val dannyTrejoEnabled = myPreference?.getBoolean("dannyTrejo", defValue = false) ?: false
        val selectedRanges = myPreference?.getSelectedCardRanges(fortHendrixEnabled, dannyTrejoEnabled)
            ?: setOf(1..18, 19..36, 37..40)

        val cards: MutableList<Card> = mutableListOf()
        selectedRanges.sortedBy { it.first }.forEach { range ->
            val startIndex = (range.first - 1).coerceIn(0, allCards.size)
            val endIndex = range.last.coerceIn(startIndex, allCards.size)
            if (startIndex < endIndex) {
                cards.addAll(allCards.subList(startIndex, endIndex))
            }
        }
        deck = cards.shuffled()
    }

    fun nextCard() {
        var currentCardIndex = _uiState.value.currentCardIndex
        // If we are at the last card, shuffle the deck and start over
        if (isLastCard()) {
            resetDeck()
            currentCardIndex = -1
        }
        val nextCardIndex = currentCardIndex + 1
        val nextCard = deck[nextCardIndex]
        isForward = true
        _uiState.value = _uiState.value.copy(
            currentCardIndex = nextCardIndex,
            currentCard = nextCard,
            abominationJustDrawn = false,
        )
    }

    fun drawNewAbomination() {
        val currentAbomination = abominationRepository.getAvailableAbominations().random()
        _uiState.value = _uiState.value.copy(
            currentAbomination = currentAbomination,
            abominationJustDrawn = true
        )
    }

    fun previousCard() {
        val currentCardIndex = _uiState.value.currentCardIndex
        if (currentCardIndex <= 0) return
        val previousCardIndex = currentCardIndex - 1
        val previousCard = deck[previousCardIndex]
        isForward = false
        _uiState.value = _uiState.value.copy(
            currentCardIndex = previousCardIndex,
            currentCard = previousCard,
            abominationJustDrawn = false,
        )
    }

    fun toggleMute() {
        _isMuted.value = _isMuted.value.not()
        myPreference?.setBoolean("isMuted", _isMuted.value)
    }

    fun getIsMuted(): Boolean {
        return _isMuted.value
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

    fun isForward(): Boolean {
        return isForward
    }

    fun increaseDangerLevel() {
        _uiState.value = _uiState.value.copy(currentDanger = _uiState.value.currentDanger.next())
    }

    fun decreaseDangerLevel() {
        _uiState.value = _uiState.value.copy(currentDanger = _uiState.value.currentDanger.previous())
    }
}
