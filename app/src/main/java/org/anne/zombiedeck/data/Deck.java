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

public class Deck {
    List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public void initializeDeckFromJson(Context context) {
        String jsonFileString = readJsonFile(context, R.raw.cards);
        cards = parseJsonToCards(jsonFileString);
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
            return objectMapper.readValue(jsonString, new TypeReference<List<Card>>() {});
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
