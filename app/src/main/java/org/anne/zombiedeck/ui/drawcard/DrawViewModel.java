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

    final MutableLiveData<Card> currentCard = new MutableLiveData<>();
    final MutableLiveData<Abomination> currentAbomination = new MutableLiveData<>();
    final MutableLiveData<Danger> currentDanger = new MutableLiveData<>(Danger.BLUE);
    final MutableLiveData<Boolean> isLastCard = new MutableLiveData<>(false);

    /**
     * Initialize the deck and draw the first card if not already started
     * or draw the next card if already started
     */
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

    /**
     * Draw the previous card if not already at the beginning of the deck
     */
    public void previousCard() {
        currentCardIndex--;
        if (currentCardIndex < 0) {
            return;
        }
        currentCard.postValue(deck.get(currentCardIndex));
    }

    /**
     * Draw a random abomination
     */
    public void drawAbomination() {
        int index = (int) (Math.random() * abominations.size());
        currentAbomination.postValue(abominations.get(index));
        firstAbominationDrawn = true;
    }

    /**
     * @return true if the current card is the first card of the deck
     */
    public boolean isFirstCard() {
        return currentCardIndex == 0;
    }

    /**
     * @return the progress of the draw in percentage
     */
    public int getProgress() {
        if (deck == null || deck.isEmpty()) {
            return 0;
        }
        return (currentCardIndex + 1) * 100 / deck.size();
    }

    /**
     * @return true if an abomination has already been drawn
     */
    public boolean hasAbomination() {
        return firstAbominationDrawn;
    }

    /**
     * lower the danger level
     */
    public void previousDangerLevel() {
        currentDanger.postValue(Objects.requireNonNull(currentDanger.getValue()).previous());
    }

    /**
     * increase the danger level
     */
    public void nextDangerLevel() {
        currentDanger.postValue(Objects.requireNonNull(currentDanger.getValue()).next());
    }
}
