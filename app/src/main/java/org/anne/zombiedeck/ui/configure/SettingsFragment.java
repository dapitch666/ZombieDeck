package org.anne.zombiedeck.ui.configure;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import org.anne.zombiedeck.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        final SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        assert prefs != null;
        SwitchPreferenceCompat cards1To18 = findPreference("cards_1_to_18");
        // Do not allow all switches to be off
        prefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (key != null && (key.equals("cards_1_to_18") ||
                            key.equals("cards_19_to_36") ||
                            key.equals("cards_37_to_40"))) {
                if (!prefs.getBoolean("cards_1_to_18", false) &&
                        !prefs.getBoolean("cards_19_to_36", false) &&
                        !prefs.getBoolean("cards_37_to_40", false)) {
                    assert cards1To18 != null;
                    showDialog();
                    cards1To18.setChecked(true);
                }
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.preferences_alert)
                .setPositiveButton(R.string.positive, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}