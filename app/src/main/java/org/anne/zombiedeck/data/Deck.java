package org.anne.zombiedeck.data;


import android.content.Context;

import org.anne.zombiedeck.R;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Deck of zombie cards
 * Contains a list of cards initialized from a JSON file
 * Filtered by the user's preferences in the settings
 * @see Card
 * @see org.anne.zombiedeck.data.DeckRepository
 */
public class Deck {
    List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public void initializeDeckFromJson(Context context, ArrayList<Integer> allowedCards) {
        String jsonFileString = readJsonFile(context, R.raw.cards);
        cards = parseJsonToCards(jsonFileString).stream()
                .filter(card -> allowedCards.contains(card.getId()))
                .collect(Collectors.toList());
    }

    public List<Card> getCards() {
        List<Card> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards);
        return shuffledCards;
    }

    public String readJsonFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    public List<Card> parseJsonToCards(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static Deck instance;
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }
}
