package org.anne.zombideck.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import org.anne.zombideck.data.Expansion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MyPreference @Inject constructor(@ApplicationContext context: Context?) {
    private val prefs: SharedPreferences? =
        context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

    private companion object {
        const val PREF_SELECTED_CARD_RANGES = "selected_card_ranges"
    }

    open fun getBoolean(key: String, defValue: Boolean = true): Boolean {
        return prefs?.getBoolean(key, defValue) ?: defValue
    }

    open fun setBoolean(key: String, value: Boolean) {
        prefs?.edit { putBoolean(key, value) }
    }

    open fun getSelectedCardRanges(enabledExpansions: Set<Expansion>): Set<IntRange> {
        val storedRanges = prefs
            ?.getStringSet(PREF_SELECTED_CARD_RANGES, null)
            ?.mapNotNull { parseRange(it) }
            ?.toSet()

        if (!storedRanges.isNullOrEmpty()) {
            return storedRanges
        }

        return buildDefaultRanges(enabledExpansions)
            .also { setSelectedCardRanges(it) }
    }

    open fun setSelectedCardRanges(ranges: Set<IntRange>) {
        val serializedRanges = ranges.map { "${it.first}-${it.last}" }.toSet()
        prefs?.edit { putStringSet(PREF_SELECTED_CARD_RANGES, serializedRanges) }
    }

    // ── Private helpers ──────────────────────────────────────────────────────────

    /**
     * Builds the default selected ranges for a fresh install (or after a prefs wipe).
     * - Expansions with difficulty sub-ranges (BASE, FORT_HENDRIX): all three sub-ranges enabled.
     * - Expansions with only a cardRange (DANNY_TREJO): the full range is included.
     * - Expansions with no cardRange (URBAN_LEGENDS): nothing to add.
     */
    private fun buildDefaultRanges(enabledExpansions: Set<Expansion>): Set<IntRange> {
        val defaults = mutableSetOf<IntRange>()
        for (expansion in enabledExpansions) {
            if (expansion.hasDifficultyRanges) {
                expansion.easyRange?.let  { defaults.add(it) }
                expansion.hardRange?.let  { defaults.add(it) }
                expansion.extraRange?.let { defaults.add(it) }
            } else {
                expansion.cardRange?.let { defaults.add(it) }
            }
        }
        return defaults
    }

    private fun parseRange(value: String): IntRange? {
        val parts = value.split("-")
        if (parts.size != 2) return null
        val start = parts[0].toIntOrNull() ?: return null
        val end   = parts[1].toIntOrNull() ?: return null
        if (start > end) return null
        return start..end
    }
}