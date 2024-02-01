package org.anne.zombiedeck.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.anne.zombiedeck.data.Deck;
import org.anne.zombiedeck.data.DeckRepository;
import org.anne.zombiedeck.ui.drawcard.DrawViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final DeckRepository deckRepository;
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    private ViewModelFactory(Context context) {
        Deck deck = Deck.getInstance();
        this.deckRepository = new DeckRepository(context, deck);
    }

    @Override
    @NotNull
    public <T extends ViewModel>  T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DrawViewModel.class)) {
            return (T) new DrawViewModel(deckRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
