package org.anne.zombiedeck.ui.drawcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.compose.ui.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawFragment extends Fragment {
    private DrawViewModel viewModel;
    private FragmentDrawBinding binding;
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
        binding.dangerLevelBackButton.setOnClickListener(v -> viewModel.previousDangerLevel());
        binding.dangerLevelForwardButton.setOnClickListener(v -> viewModel.nextDangerLevel());
        binding.previousCardButton.setOnClickListener(this::previousCard);
        binding.nextCardButton.setOnClickListener(v -> viewModel.nextCard());
        binding.drawAbominationButton.setOnClickListener(this::drawAbomination);
        binding.displayAbominationButton.setOnClickListener(this::displayAbomination);
        viewModel.currentAbomination.observe(getViewLifecycleOwner(), this::displayAbomination);
        viewModel.currentCard.observe(getViewLifecycleOwner(), this::displayCard);
        viewModel.currentDanger.observe(getViewLifecycleOwner(), this::updateDangerLevel);
        viewModel.isLastCard.observe(getViewLifecycleOwner(),
                b -> binding.nextCardButton.setText(b ? R.string.shuffle : R.string.draw_a_card));
        updateDangerLevel(Danger.BLUE);
    }

    private void updateDangerLevel(Danger danger) {
        Drawable dangerProgressBarBackground = binding.dangerProgressBarBg.getBackground();
        GradientDrawable dangerProgressBarBackgroundColor = (GradientDrawable) dangerProgressBarBackground;
        dangerProgressBarBackgroundColor.setColor(ContextCompat.getColor(context, danger.getBgColor()));
        binding.dangerLevelText.setText(getString(R.string.danger_level, getString(danger.getName())));
        if (danger != Danger.BLUE) {
            binding.dangerLevelBackButton.setIconTintResource(danger.previous().getBgColor());
            binding.dangerLevelBackButton.setVisibility(View.VISIBLE);
        } else {
            binding.dangerLevelBackButton.setVisibility(View.INVISIBLE);
        }
        if (danger != Danger.RED) {
            binding.dangerLevelForwardButton.setIconTintResource(danger.next().getBgColor());
            binding.dangerLevelForwardButton.setVisibility(View.VISIBLE);
        } else {
            binding.dangerLevelForwardButton.setVisibility(View.INVISIBLE);
        }
        refreshDangerLevelDisplay();
    }

    private void previousCard(View view) {
        if (viewModel.isFirstCard()) {
            return;
        }
        viewModel.previousCard();
        binding.card.setEnabled(true);
    }

    private void displayAbomination(Abomination abomination) {
        DialogFragment dialog = AbominationDialogFragment.newInstance(abomination.name());
        if (dialog.getDialog() != null && dialog.getDialog().getWindow() != null) {
            dialog.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show(getChildFragmentManager(), "AbominationDialogFragment");
    }

    private void displayAbomination(View view) {
        if (viewModel.currentAbomination.getValue() != null) {
            displayAbomination(viewModel.currentAbomination.getValue());
        }
    }

    private void drawAbomination(View view) {
        viewModel.drawAbomination();
        toggleAbominationButton(false);
    }

    @SuppressLint("DefaultLocale")
    private void displayCard(Card card) {
        ZombieType zombieType = card.getZombieType();
        binding.cardImage.setImageDrawable(
                AppCompatResources.getDrawable(context, zombieType.getImage()));
        binding.cardId.setText(getString(R.string.card_number, String.format("%03d", card.getId())));
        binding.cardZombie.setText(getText(zombieType.getName()));
        LayerDrawable bgCardTop = (LayerDrawable) binding.bgCardTop.getDrawable();
        GradientDrawable stripe = (GradientDrawable) bgCardTop.findDrawableByLayerId(R.id.stripe_bg);
        GradientDrawable cardTop = (GradientDrawable) bgCardTop.findDrawableByLayerId(R.id.top_bg);
        switch (card.getCardType()) {
            case RUSH -> {
                binding.cardAction.setText(getString(R.string.spawn_then_activate));
                binding.cardAction.setVisibility(View.VISIBLE);
                stripe.setColor(ContextCompat.getColor(context, R.color.danger_yellow));
                cardTop.setColor(ContextCompat.getColor(context, R.color.danger_yellow));
                binding.cardZombie.setBackgroundColor(ContextCompat.getColor(context, R.color.danger_yellow));
                binding.cardZombie.setTextColor(ContextCompat.getColor(context, R.color.black));
                binding.cardId.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
            case EXTRA_ACTIVATION -> {
                binding.cardAction.setText(getString(R.string.one_extra_activation));
                binding.cardAction.setVisibility(View.VISIBLE);
                stripe.setColor(ContextCompat.getColor(context, R.color.danger_red));
                cardTop.setColor(ContextCompat.getColor(context, R.color.danger_red));
                binding.cardZombie.setBackgroundColor(ContextCompat.getColor(context, R.color.danger_red));
                binding.cardZombie.setTextColor(ContextCompat.getColor(context, R.color.white));
                binding.cardId.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
            case SPAWN -> {
                binding.cardAction.setVisibility(View.INVISIBLE);
                stripe.setColor(ContextCompat.getColor(context, R.color.white));
                cardTop.setColor(ContextCompat.getColor(context, R.color.black));
                binding.cardZombie.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
                binding.cardZombie.setTextColor(ContextCompat.getColor(context, R.color.white));
                binding.cardId.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
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
        // Progress bar
        binding.dangerProgressBar.setProgress(viewModel.getProgress());
        // Display card
        if (binding.card.getVisibility() == View.INVISIBLE) {
            binding.card.setVisibility(View.VISIBLE);
        }
    }


    private void refreshDangerLevelDisplay() {
        if (viewModel.currentCard.getValue() != null && viewModel.currentDanger.getValue() != null) {
            Card card = viewModel.currentCard.getValue();
            Danger dangerLevel = viewModel.currentDanger.getValue();
            binding.zombieAmount.setText(getString(R.string.amount, card.getAmount(dangerLevel)));
            binding.zombieAmount.setTextColor(ContextCompat.getColor(context, dangerLevel.getTextColor()));
            Drawable background = binding.zombieAmount.getBackground();
            background.setColorFilter(ContextCompat.getColor(context, dangerLevel.getBgColor()), PorterDuff.Mode.SRC_ATOP);
            toggleAbominationButton(card.getZombieType() == ZombieType.ABOMINATION
                    && card.getAmount(dangerLevel) > 0);
        }
    }

    private void toggleAbominationButton(boolean drawNewAbomination) {
        binding.displayAbominationButton.setVisibility(drawNewAbomination ? View.GONE : View.VISIBLE);
        binding.displayAbominationButton.setEnabled(!drawNewAbomination && viewModel.hasAbomination());
        binding.drawAbominationButton.setEnabled(drawNewAbomination);
        binding.drawAbominationButton.setVisibility(drawNewAbomination ? View.VISIBLE : View.GONE);
    }
}