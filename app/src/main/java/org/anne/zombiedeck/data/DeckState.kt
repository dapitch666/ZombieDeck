package org.anne.zombiedeck.data

data class DeckState(
    val currentCardIndex: Int = -1,
    val currentCard: Card? = null,
    val abominationJustDrawn: Boolean = false,
    val currentAbomination: Abomination? = null,
    val currentDanger: Danger = Danger.BLUE,
)