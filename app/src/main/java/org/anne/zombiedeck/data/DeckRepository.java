package org.anne.zombiedeck.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Repository for the deck of cards
 */
public class DeckRepository {
    private final Deck deck;
    private final Context context;
    private final SharedPreferences prefs;

    public DeckRepository(Context context, Deck deck) {
        this.deck = deck;
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public List<Card> getCards() {
        var allowedCards = new ArrayList<Integer>();
        if (prefs.getBoolean("cards_1_to_18", true)) {
            allowedCards.addAll(addCardsToDeck(1, 18));
        }
        if (prefs.getBoolean("cards_19_to_36", true)) {
            allowedCards.addAll(addCardsToDeck(19, 36));
        }
        if (prefs.getBoolean("cards_37_to_40", true)) {
            allowedCards.addAll(addCardsToDeck(37, 40));
        }
        deck.initializeDeckFromJson(context, allowedCards);
        return deck.getCards();
    }

    private List<Integer> addCardsToDeck(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
