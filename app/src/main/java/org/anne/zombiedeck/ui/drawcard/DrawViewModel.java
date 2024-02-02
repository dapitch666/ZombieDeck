package org.anne.zombiedeck.ui.drawcard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.anne.zombiedeck.data.Card;
import org.anne.zombiedeck.data.DeckRepository;

import java.util.List;

public class DrawViewModel extends ViewModel {
    private final DeckRepository deckRepository;
    private List<Card> deck;
    private Integer currentCardIndex;

    public DrawViewModel(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    MutableLiveData<Card> currentCard = new MutableLiveData<>();
    MutableLiveData<Boolean> isStarted = new MutableLiveData<>(false);

    public void start() {
        deck = deckRepository.getCards();
        currentCardIndex = 0;
        currentCard.postValue(deck.get(currentCardIndex));
        isStarted.postValue(true);
    }

    public void nextCard() {
        currentCardIndex++;
        if (currentCardIndex >= deck.size()) {
            return;
        }
        currentCard.postValue(deck.get(currentCardIndex));
    }

    public void previousCard() {
        currentCardIndex--;
        if (currentCardIndex < 0) {
            return;
        }
        currentCard.postValue(deck.get(currentCardIndex));
    }

    public boolean isFirstCard() {
        return currentCardIndex == 0;
    }

    public boolean isLastCard() {
        return currentCardIndex == deck.size() - 1;
    }

    public int getProgress() {
        return currentCardIndex * 100 / deck.size();
    }
}
