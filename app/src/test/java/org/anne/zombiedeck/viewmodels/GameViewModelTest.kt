package org.anne.zombiedeck.viewmodels

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.anne.zombiedeck.data.Danger
import org.junit.Assert.assertThrows
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel(null)

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
    fun gameViewModel_LastCard_DeckInitialized() {
        for (i in 0 until 40) {
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
}