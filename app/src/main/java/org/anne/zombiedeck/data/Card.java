package org.anne.zombiedeck.data;

import java.util.List;

/**
 * Class for the card
 * Used to load the data from the json file
 */
public class Card {
    int id;
    CardType cardType;
    ZombieType zombieType;
    List<Integer> amount;

    public Card() {
        super();
    }

    public Card(int id, CardType cardType, ZombieType zombieType, List<Integer> amount) {
        this.id = id;
        this.cardType = cardType;
        this.zombieType = zombieType;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public ZombieType getZombieType() {
        return zombieType;
    }

    public void setZombieType(ZombieType zombieType) {
        this.zombieType = zombieType;
    }

    public int getAmount(Danger dangerLevel) {
        return this.amount.get(dangerLevel.getIndex());
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public boolean isExtraActivation() {
        return this.cardType == CardType.EXTRA_ACTIVATION;
    }
}
