package org.anne.zombiedeck.ui.drawcard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.anne.zombiedeck.data.Card;
import org.anne.zombiedeck.data.DeckRepository;

import java.util.List;

public class DrawViewModel extends ViewModel {
    private final DeckRepository deckRepository;
    private List<Card> deck;
    private Integer currentCardIndex = 0;

    public DrawViewModel(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    MutableLiveData<Card> currentCard = new MutableLiveData<>();
    MutableLiveData<Boolean> isLastCard = new MutableLiveData<>(false);
    MutableLiveData<Integer> dangerLevel = new MutableLiveData<>(0);
    MutableLiveData<Boolean> isStarted = new MutableLiveData<>(false);

    public void start() {
        deck = deckRepository.getCards();
        currentCard.postValue(deck.get(0));
        isStarted.postValue(true);
        isLastCard.postValue(false);
    }

    public void drawCard() {
        currentCardIndex++;
        if (currentCardIndex >= deck.size()) {
            return;
        } else if (currentCardIndex == deck.size() - 1) {
            isLastCard.postValue(true);
        }
        currentCard.postValue(deck.get(currentCardIndex));
    }
}
