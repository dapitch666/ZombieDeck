package org.anne.zombiedeck.ui.drawcard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.anne.zombiedeck.R;
import org.anne.zombiedeck.data.Abomination;
import org.anne.zombiedeck.data.Card;
import org.anne.zombiedeck.data.Danger;
import org.anne.zombiedeck.data.ZombieType;
import org.anne.zombiedeck.databinding.FragmentDrawBinding;
import org.anne.zombiedeck.injection.ViewModelFactory;
import org.anne.zombiedeck.ui.abominations.AbominationDialogFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawFragment extends Fragment {
    private DrawViewModel viewModel;
    private FragmentDrawBinding binding;
    private Danger dangerLevel = Danger.BLUE;
    private Context context;

    public DrawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrawCardFragment.
     */
    public static DrawFragment newInstance() {
        return new DrawFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireActivity().getApplicationContext();
        viewModel = new ViewModelProvider(this,
                ViewModelFactory.getInstance(context))
                .get(DrawViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDrawBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Spinner
        final Spinner spinner = binding.spinnerDanger;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.danger_levels,
                R.layout.spinner_list
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dangerLevel = Danger.valueOf(position);
                int backgroundResource = switch (dangerLevel) {
                    case BLUE -> R.drawable.bg_spinner_blue;
                    case YELLOW -> R.drawable.bg_spinner_yellow;
                    case ORANGE -> R.drawable.bg_spinner_orange;
                    case RED -> R.drawable.bg_spinner_red;
                };
                spinner.setBackgroundResource(backgroundResource);
                spinner.setPopupBackgroundResource(dangerLevel.getColor());
                refreshDangerLevelDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(dangerLevel.getIndex());
            }
        });
        // End spinner
        binding.previousCardButton.setOnClickListener(this::previousCard);
        binding.nextCardButton.setOnClickListener(this::drawCard);
        binding.drawAbominationButton.setOnClickListener(v -> viewModel.drawAbomination());
        binding.seeAbominationButton.setOnClickListener(this::displayAbomination);
        viewModel.currentAbomination.observe(getViewLifecycleOwner(), this::displayAbomination);
        viewModel.currentCard.observe(getViewLifecycleOwner(), this::displayCard);
    }

    private void previousCard(View view) {
        if (viewModel.isFirstCard()) {
            return;
        }
        viewModel.previousCard();
        binding.card.setEnabled(true);
    }

    private void drawCard(View view) {
        if (Boolean.FALSE.equals(viewModel.isStarted.getValue()) ||
                viewModel.isLastCard()) {
            viewModel.start();
        } else {
            viewModel.nextCard();
        }
    }

    private void displayAbomination(Abomination abomination) {
        DialogFragment dialog = AbominationDialogFragment.newInstance(abomination.name());
        if (dialog.getDialog() != null && dialog.getDialog().getWindow() != null) {
            dialog.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show(getChildFragmentManager(), "AbominationDialogFragment");
    }

    private void displayAbomination(View view) {
        displayAbomination(Objects.requireNonNull(viewModel.currentAbomination.getValue()));
    }

    private void displayCard(Card card) {
        int zombieImage;
        int zombieName;
        switch (card.getZombieType()) {
            case WALKER -> {
                zombieName = R.string.walker;
                zombieImage = R.drawable.walker;
            }
            case RUNNER -> {
                zombieName = R.string.runner;
                zombieImage = R.drawable.runner;
            }
            case FATTY -> {
                zombieName = R.string.fatty;
                zombieImage = R.drawable.fatty;
            }
            case ABOMINATION -> {
                zombieName = R.string.abomination;
                zombieImage = R.drawable.abomination;
            }
            default -> throw new IllegalStateException("Unexpected value: " + card.getZombieType());
        }
        binding.cardImage.setImageDrawable(
                AppCompatResources.getDrawable(context, zombieImage));
        binding.cardId.setText(getString(R.string.card_number, getCardId(card.getId())));
        binding.cardZombie.setText(getText(zombieName));
        switch (card.getCardType()) {
            case RUSH -> {
                binding.cardAction.setText(getString(R.string.spawn_then_activate));
                binding.cardAction.setVisibility(View.VISIBLE);
            }
            case EXTRA_ACTIVATION -> {
                binding.cardAction.setText(getString(R.string.one_extra_activation));
                binding.cardAction.setVisibility(View.VISIBLE);
            }
            case SPAWN -> binding.cardAction.setVisibility(View.INVISIBLE);
        }
        // Flip card
        if (binding.cardFront.getVisibility() == View.INVISIBLE) {
            binding.cardFront.setVisibility(View.VISIBLE);
            binding.bgCard.setImageDrawable(
                    AppCompatResources.getDrawable(context, R.drawable.bg_card));
        }
        // Display number of zombies for the selected danger level
        refreshDangerLevelDisplay();
        // Display buttons
        binding.previousCardButton.setEnabled(!viewModel.isFirstCard());
        binding.nextCardButton.setText(viewModel.isLastCard() ? R.string.shuffle : R.string.draw_a_card);
        // Progress bar
        binding.determinateBar.setProgress(viewModel.getProgress());
        // Display card
        if (binding.card.getVisibility() == View.INVISIBLE) {
            binding.card.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    private String getCardId(int id) {
        return String.format("%03d", id);
    }

    private void refreshDangerLevelDisplay() { // TODO: rename
        if (viewModel.currentCard.getValue() != null) {
            Card card = viewModel.currentCard.getValue();
            binding.zombieAmount.setText(getString(R.string.amount, card.getAmount(dangerLevel)));
            Drawable background = binding.zombieAmount.getBackground();
            background.setColorFilter(ContextCompat.getColor(context, dangerLevel.getColor()), PorterDuff.Mode.SRC_ATOP);
            // Propose to draw an abomination
            binding.drawAbominationButton.setEnabled(card.getZombieType() == ZombieType.ABOMINATION
                    && card.getAmount(dangerLevel) > 0);
        }
    }
}