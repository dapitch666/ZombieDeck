package org.anne.zombiedeck.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MyPreference @Inject constructor(@ApplicationContext context : Context?){
    private val prefs: SharedPreferences? = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

    private companion object {
        const val PREF_SELECTED_CARD_RANGES = "selected_card_ranges"
    }

    open fun getBoolean(key: String, defValue: Boolean = true): Boolean {
        return prefs?.getBoolean(key, defValue) ?: defValue
    }

    open fun setBoolean(key: String, value: Boolean) {
        prefs?.edit { putBoolean(key, value) }
    }

    open fun getSelectedCardRanges(fortHendrixEnabled: Boolean): Set<IntRange> {
        val storedRanges = prefs
            ?.getStringSet(PREF_SELECTED_CARD_RANGES, null)
            ?.mapNotNull { parseRange(it) }
            ?.toSet()

        if (!storedRanges.isNullOrEmpty()) {
            return storedRanges
        }

        // Migrate legacy boolean preferences to explicit ranges on first read.
        val migratedRanges = mutableSetOf<IntRange>()
        if (getBoolean("cards_1_to_18")) {
            migratedRanges += if (fortHendrixEnabled) 41..58 else 1..18
        }
        if (getBoolean("cards_19_to_36")) {
            migratedRanges += if (fortHendrixEnabled) 59..76 else 19..36
        }
        if (getBoolean("cards_37_to_40")) {
            migratedRanges += if (fortHendrixEnabled) 77..80 else 37..40
        }

        val safeRanges = migratedRanges.ifEmpty {
            if (fortHendrixEnabled) {
                setOf(41..58, 59..76, 77..80)
            } else {
                setOf(1..18, 19..36, 37..40)
            }
        }
        setSelectedCardRanges(safeRanges)
        return safeRanges
    }

    open fun setSelectedCardRanges(ranges: Set<IntRange>) {
        val serializedRanges = ranges.map { "${it.first}-${it.last}" }.toSet()
        prefs?.edit { putStringSet(PREF_SELECTED_CARD_RANGES, serializedRanges) }
    }

    private fun parseRange(value: String): IntRange? {
        val split = value.split("-")
        if (split.size != 2) return null
        val start = split[0].toIntOrNull() ?: return null
        val end = split[1].toIntOrNull() ?: return null
        if (start > end) return null
        return start..end
    }
}