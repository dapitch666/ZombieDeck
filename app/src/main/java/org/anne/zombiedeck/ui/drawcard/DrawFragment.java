package org.anne.zombiedeck.ui.drawcard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.anne.zombiedeck.R;
import org.anne.zombiedeck.data.Card;
import org.anne.zombiedeck.databinding.FragmentDrawBinding;
import org.anne.zombiedeck.injection.ViewModelFactory;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrawFragment extends Fragment {
    private DrawViewModel viewModel;
    private FragmentDrawBinding binding;
    private int dangerLevel = 0;
    private Context context;
    private static final List<Integer> colorList = Arrays.asList(
            R.color.danger_blue, R.color.danger_yellow,
            R.color.danger_orange, R.color.danger_red);

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
                ViewModelFactory.getInstance(requireActivity().getApplicationContext()))
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
                requireActivity().getApplicationContext(),
                R.array.danger_levels,
                R.layout.spinner_list
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dangerLevel = position;
                int backgroundResource = switch (position) {
                    case 0 -> R.drawable.bg_spinner_blue;
                    case 1 -> R.drawable.bg_spinner_yellow;
                    case 2 -> R.drawable.bg_spinner_orange;
                    case 3 -> R.drawable.bg_spinner_red;
                    default -> R.drawable.bg_spinner;
                };
                spinner.setBackgroundResource(backgroundResource);
                spinner.setPopupBackgroundResource(colorList.get(dangerLevel));
                updateDangerLevel(dangerLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(dangerLevel);
            }
        });
        // End spinner
        binding.drawCard.setOnClickListener(this::drawCard);
    }

    private void drawCard(View view) {
        if (Boolean.FALSE.equals(viewModel.isStarted.getValue())) {
            viewModel.start();
        } else if (Boolean.TRUE.equals(viewModel.isLastCard.getValue())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.last_card);
            builder.setPositiveButton(R.string.shuffle_and_start, (dialog, which) -> viewModel.start());
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            viewModel.drawCard();
        }
        viewModel.currentCard.observe(getViewLifecycleOwner(), this::showCard);
        binding.card.setVisibility(View.VISIBLE);
    }

    private void showCard(Card card) {
        int zombieImage = switch (card.getZombieType()) {
            case WALKER -> R.drawable.walker;
            case RUNNER -> R.drawable.runner;
            case FATTY -> R.drawable.fatty;
            case ABOMINATION -> R.drawable.abomination;
        };
        binding.cardImage.setImageDrawable(
                AppCompatResources.getDrawable(requireActivity().getApplicationContext(), zombieImage));
        // binding.cardImage.setImageDrawable(null);
        // binding.cardImage.setImageDrawable(
        //        AppCompatResources.getDrawable(context, R.drawable.walker)
        //);
        binding.cardId.setText(getString(R.string.card_number, getCardId(card.getId())));
        binding.cardZombie.setText(card.getZombieType().toString());
        binding.zombieAmount.setText(getString(R.string.amount, card.getAmount().get(dangerLevel)));
        binding.zombieAmount.setBackgroundColor(colorList.get(dangerLevel));
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
    }

    @SuppressLint("DefaultLocale")
    private String getCardId(int id) {
        return String.format("%03d", id);
    }

    private void updateDangerLevel(Integer integer) {
        if (viewModel.currentCard.getValue() != null) {
            Card card = viewModel.currentCard.getValue();
            binding.zombieAmount.setText(getString(R.string.amount, card.getAmount().get(dangerLevel)));
            binding.zombieAmount.setBackgroundColor(colorList.get(dangerLevel));
        }
    }
}