package org.anne.zombideck.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.anne.zombideck.data.AbominationRepository
import org.anne.zombideck.data.Card
import org.anne.zombideck.data.DeckState
import org.anne.zombideck.data.Expansion
import org.anne.zombideck.data.allCards
import org.anne.zombideck.settings.MyPreference
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val myPreference: MyPreference?,
) : ViewModel() {

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

    // ── Deck management ──────────────────────────────────────────────────────────

    private fun resetDeck() {
        val enabledExpansions = enabledExpansions()
        val enabledSubRanges = enabledSubRanges(enabledExpansions)

        deck = allCards
            .filter { card -> card.expansion in enabledExpansions }
            .filter { card -> card.id in enabledSubRanges }
            .shuffled()
    }

    /**
     * Returns the set of expansions the player has switched on.
     * BASE is always included.
     */
    private fun enabledExpansions(): Set<Expansion> =
        Expansion.entries.filterTo(mutableSetOf()) { expansion ->
            expansion == Expansion.BASE ||
                    (myPreference?.getBoolean(expansion.prefKey, defValue = false) ?: false)
        }

    /**
     * Returns the union of all difficulty sub-ranges the player has selected,
     * or null if sub-range filtering should not apply (e.g. Danny Trejo, which
     * has no difficulty sub-ranges — its cards are always included entirely).
     *
     * The sub-range selection is only meaningful for expansions that declare
     * easyRange / hardRange / extraRange (BASE and FORT_HENDRIX).
     */
    private fun enabledSubRanges(enabledExpansions: Set<Expansion>): Set<Int> {

         val selectedRanges = myPreference
            ?.getSelectedCardRanges(enabledExpansions)
            ?: setOf(
                Expansion.BASE.easyRange!!,
                Expansion.BASE.hardRange!!,
                Expansion.BASE.extraRange!!,
            )

        // Flatten all IntRanges into a single Set<Int> for O(1) lookup.
        return selectedRanges.flatMapTo(mutableSetOf()) { it.toList() }
    }

    // ── Navigation ───────────────────────────────────────────────────────────────

    fun nextCard() {
        var currentCardIndex = _uiState.value.currentCardIndex
        if (isLastCard()) {
            resetDeck()
            currentCardIndex = -1
        }
        val nextCardIndex = currentCardIndex + 1
        isForward = true
        _uiState.value = _uiState.value.copy(
            currentCardIndex = nextCardIndex,
            currentCard = deck[nextCardIndex],
            abominationJustDrawn = false,
        )
    }

    fun previousCard() {
        val currentCardIndex = _uiState.value.currentCardIndex
        if (currentCardIndex <= 0) return
        val previousCardIndex = currentCardIndex - 1
        isForward = false
        _uiState.value = _uiState.value.copy(
            currentCardIndex = previousCardIndex,
            currentCard = deck[previousCardIndex],
            abominationJustDrawn = false,
        )
    }

    // ── Abomination ──────────────────────────────────────────────────────────────

    fun drawNewAbomination() {
        val currentAbomination = abominationRepository.getAvailableAbominations().random()
        _uiState.value = _uiState.value.copy(
            currentAbomination = currentAbomination,
            abominationJustDrawn = true,
        )
    }

    // ── Danger level ─────────────────────────────────────────────────────────────

    fun increaseDangerLevel() {
        _uiState.value = _uiState.value.copy(currentDanger = _uiState.value.currentDanger.next())
    }

    fun decreaseDangerLevel() {
        _uiState.value =
            _uiState.value.copy(currentDanger = _uiState.value.currentDanger.previous())
    }

    // ── Mute ─────────────────────────────────────────────────────────────────────

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
        myPreference?.setBoolean("isMuted", _isMuted.value)
    }

    fun getIsMuted(): Boolean = _isMuted.value

    // ── State queries ─────────────────────────────────────────────────────────────

    fun getProgress(): Float = (_uiState.value.currentCardIndex.toFloat() + 1) / deck.size

    fun isLastCard(): Boolean = _uiState.value.currentCardIndex == deck.size - 1

    fun isFirstCard(): Boolean = _uiState.value.currentCardIndex <= 0

    fun isForward(): Boolean = isForward
}