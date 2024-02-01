package org.anne.zombiedeck.data;

import android.content.Context;

import java.util.List;

public class DeckRepository {
    private final Deck deck;
    private final Context context;

    public DeckRepository(Context context, Deck deck) {
        this.deck = deck;
        this.context = context;
    }

    public List<Card> getCards() {
        deck.initializeDeckFromJson(context);
        return deck.getCards();
    }
}
