package org.anne.zombiedeck.ui.abominations;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import org.anne.zombiedeck.R;
import org.anne.zombiedeck.data.Abomination;
import org.anne.zombiedeck.databinding.FragmentAbominationDialogBinding;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link AbominationDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbominationDialogFragment extends DialogFragment {

    private FragmentAbominationDialogBinding binding;
    private static final String ARG_PARAM = "param";

    private Abomination abomination;

    public AbominationDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param abominationName Name of the abomination.
     * @return A new instance of fragment AbominationDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AbominationDialogFragment newInstance(String abominationName) {
        AbominationDialogFragment fragment = new AbominationDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, abominationName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            abomination = Abomination.valueOf(getArguments().getString(ARG_PARAM));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAbominationDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        binding.abominationName.setText(getString(abomination.getName()));
        binding.abominationImage.setImageResource(abomination.getImage());
        binding.abominationRule.setText(getString(abomination.getRule()));
        LayerDrawable bgAbominationCardTop = (LayerDrawable) binding.bgAbominationCardTop.getDrawable();
        GradientDrawable stripe = (GradientDrawable) bgAbominationCardTop.findDrawableByLayerId(R.id.stripe_bg);
        GradientDrawable cardTop = (GradientDrawable) bgAbominationCardTop.findDrawableByLayerId(R.id.top_bg);
        stripe.setColor(ContextCompat.getColor(requireContext(), R.color.danger_yellow));
        cardTop.setColor(ContextCompat.getColor(requireContext(), R.color.black));
        view.setOnClickListener(v -> dismiss());
    }
}