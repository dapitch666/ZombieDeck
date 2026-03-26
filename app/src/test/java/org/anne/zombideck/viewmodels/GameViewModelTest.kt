package org.anne.zombideck.viewmodels

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.anne.zombideck.data.Danger
import org.anne.zombideck.settings.MyPreference
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        viewModel = GameViewModel(null)
    }

    @Test
    fun gameViewModel_initDeck_ViewModelValuesInitialized() {
        val currentGameUiState = viewModel.uiState.value
        assertEquals(-1, currentGameUiState.currentCardIndex)
        assertNull(currentGameUiState.currentCard)
        assertFalse(currentGameUiState.abominationJustDrawn)
        assertNull(currentGameUiState.currentAbomination)
        assertEquals(Danger.BLUE, currentGameUiState.currentDanger)
        assertTrue(viewModel.isFirstCard())
        assertFalse(viewModel.isLastCard())
    }

    @Test
    fun gameViewModel_nextCard_ViewModelValuesUpdated() {
        viewModel.nextCard()
        val currentGameUiState = viewModel.uiState.value
        assertEquals(0, currentGameUiState.currentCardIndex)
        assertFalse(currentGameUiState.abominationJustDrawn)
        assertNotNull(currentGameUiState.currentCard)
        assertTrue(viewModel.isFirstCard())
    }

    @Test
    fun gameViewModel_drawNewAbomination_ViewModelValuesUpdated() {
        viewModel.drawNewAbomination()
        val currentGameUiState = viewModel.uiState.value
        assertNotNull(currentGameUiState.currentAbomination)
        assertTrue(currentGameUiState.abominationJustDrawn)
    }

    @Test
    fun gameViewModel_navigation_ViewModelValuesUpdated() {
        viewModel.nextCard()
        var currentGameUiState = viewModel.uiState.value
        val firstCard = currentGameUiState.currentCard
        viewModel.nextCard()
        assertTrue(viewModel.isForward())
        viewModel.previousCard()
        assertFalse(viewModel.isForward())
        currentGameUiState = viewModel.uiState.value
        assertEquals(0, currentGameUiState.currentCardIndex)
        assertEquals(firstCard, currentGameUiState.currentCard)
        assertFalse(currentGameUiState.abominationJustDrawn)
        assertNotNull(currentGameUiState.currentCard)
        assertTrue(viewModel.isFirstCard())
    }

    @Test
    fun gameViewModel_previousCardAtStart_NoOp() {
        viewModel.previousCard()
        assertEquals(-1, viewModel.uiState.value.currentCardIndex)
        assertNull(viewModel.uiState.value.currentCard)
    }

    @Test
    fun gameViewModel_LastCard_DeckInitialized() {
        repeat(40) {
            viewModel.nextCard()
        }
        assertTrue(viewModel.isLastCard())
        viewModel.nextCard()
        val currentGameUiState = viewModel.uiState.value
        assertEquals(0, currentGameUiState.currentCardIndex)
        assertFalse(viewModel.isLastCard())
        assertTrue(viewModel.isFirstCard())
    }

    @Test
    fun gameViewModel_toggleMute_ViewModelValuesUpdated() {
        val isMuted = viewModel.getIsMuted()
        viewModel.toggleMute()
        assertEquals(!isMuted, viewModel.getIsMuted())
    }

    @Test
    fun gameViewModel_increaseDecreaseDangerLevel_ViewModelValuesUpdated() {
        viewModel.increaseDangerLevel()
        var currentGameUiState = viewModel.uiState.value
        assertEquals(Danger.YELLOW, currentGameUiState.currentDanger)
        viewModel.increaseDangerLevel()
        currentGameUiState = viewModel.uiState.value
        assertEquals(Danger.ORANGE, currentGameUiState.currentDanger)
        viewModel.increaseDangerLevel()
        currentGameUiState = viewModel.uiState.value
        assertEquals(Danger.RED, currentGameUiState.currentDanger)
        assertThrows(IllegalStateException::class.java) {
            viewModel.increaseDangerLevel()
        }
        viewModel.decreaseDangerLevel()
        currentGameUiState = viewModel.uiState.value
        assertEquals(Danger.ORANGE, currentGameUiState.currentDanger)
    }

    @Test(expected = IllegalStateException::class)
    fun gameViewModel_decreaseDangerLevelBelowBlue_ThrowsError() {
        viewModel.decreaseDangerLevel()
    }

    @Test
    fun gameViewModel_getProgress_ViewModelValuesUpdated() {
        viewModel.nextCard()
        assertEquals(1f / 40, viewModel.getProgress())
    }

    @Test
    fun gameViewModel_filterCards_DeckSizeReduced() {
        val fakePrefs = object : MyPreference(null) {
            override fun getSelectedCardRanges(
                fortHendrixEnabled: Boolean,
                dannyTrejoEnabled: Boolean,
            ): Set<IntRange> = setOf(1..18, 19..36)
        }
        
        val filteredViewModel = GameViewModel(fakePrefs)
        repeat(36) {
            filteredViewModel.nextCard()
        }
        assertTrue(filteredViewModel.isLastCard())
        assertEquals(1f, filteredViewModel.getProgress())
    }

    @Test
    fun gameViewModel_dannyTrejoEnabled_DeckIncludesExpansionCards() {
        val fakePrefs = object : MyPreference(null) {
            override fun getBoolean(key: String, defValue: Boolean): Boolean = when (key) {
                "dannyTrejo" -> true
                "fortHendrix" -> false
                else -> defValue
            }

            override fun getSelectedCardRanges(
                fortHendrixEnabled: Boolean,
                dannyTrejoEnabled: Boolean,
            ): Set<IntRange> = if (dannyTrejoEnabled) {
                setOf(1..18, 19..36, 37..40, 81..86)
            } else {
                setOf(1..18, 19..36, 37..40)
            }
        }

        val trejoViewModel = GameViewModel(fakePrefs)
        repeat(46) {
            trejoViewModel.nextCard()
        }

        assertTrue(trejoViewModel.isLastCard())
    }
}