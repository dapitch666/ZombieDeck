package org.anne.zombiedeck.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Deck(context: Context) {

    private var cards: List<Card>

    init {
        val jsonString = readJsonFromAssets(context, "cards.json")
        val cardList = parseJsonToModel(jsonString)

        this.cards = cardList
    }

    fun getCards(): List<Card> {
        return this.cards
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    private fun parseJsonToModel(jsonString: String): List<Card> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<Card>>() {}.type)
    }
}

