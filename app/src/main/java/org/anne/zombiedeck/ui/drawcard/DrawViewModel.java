package org.anne.zombiedeck.ui.drawcard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.anne.zombiedeck.data.Abomination;
import org.anne.zombiedeck.data.Card;
import org.anne.zombiedeck.data.Danger;
import org.anne.zombiedeck.data.DeckRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DrawViewModel extends ViewModel {
    private final DeckRepository deckRepository;
    private List<Card> deck;
    private final List<Abomination> abominations = Arrays.asList(Abomination.values());
    private Integer currentCardIndex = 0;
    private boolean firstAbominationDrawn = false;
    private boolean isStarted = false;

    public DrawViewModel(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    MutableLiveData<Card> currentCard = new MutableLiveData<>();
    MutableLiveData<Abomination> currentAbomination = new MutableLiveData<>();
    MutableLiveData<Danger> currentDanger = new MutableLiveData<>(Danger.BLUE);
    MutableLiveData<Boolean> isLastCard = new MutableLiveData<>(false);

    public void nextCard() {
        if (isStarted && currentCardIndex < deck.size() - 1) {
            currentCardIndex++;
            if (currentCardIndex >= deck.size()) {
                return;
            }
            currentCard.postValue(deck.get(currentCardIndex));
            if (currentCardIndex == deck.size() - 1) {
                isLastCard.postValue(true);
            }
        } else {
            deck = deckRepository.getCards();
            currentCardIndex = 0;
            currentCard.postValue(deck.get(currentCardIndex));
            isStarted = true;
            isLastCard.postValue(false);
        }
    }

    public void previousCard() {
        currentCardIndex--;
        if (currentCardIndex < 0) {
            return;
        }
        currentCard.postValue(deck.get(currentCardIndex));
    }

    public void drawAbomination() {
        int index = (int) (Math.random() * abominations.size());
        currentAbomination.postValue(abominations.get(index));
        firstAbominationDrawn = true;
    }

    public boolean isFirstCard() {
        return currentCardIndex == 0;
    }

    public int getProgress() {
        if (deck == null || deck.isEmpty()) {
            return 0;
        }
        return (currentCardIndex + 1) * 100 / deck.size();
    }

    public boolean hasAbomination() {
        return firstAbominationDrawn;
    }

    public void previousDangerLevel() {
        currentDanger.postValue(Objects.requireNonNull(currentDanger.getValue()).previous());
    }

    public void nextDangerLevel() {
        currentDanger.postValue(Objects.requireNonNull(currentDanger.getValue()).next());
    }
}
