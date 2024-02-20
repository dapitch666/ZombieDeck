package org.anne.zombiedeck.data

import android.content.Context

class DeckRepository internal constructor(
    context: Context
){
    private val deck = Deck(context)

    fun getCards(): List<Card> {
        return deck.getCards()
    }
}
