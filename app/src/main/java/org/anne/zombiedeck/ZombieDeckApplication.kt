package org.anne.zombiedeck

import android.app.Application
import org.anne.zombiedeck.data.DeckRepository

class ZombieDeckApplication: Application() {
    // private lateinit var deckRepository: DeckRepository
    lateinit var deckRepository: DeckRepository
        private set

    override fun onCreate() {
        super.onCreate()
        deckRepository = DeckRepository(this)
    }
}