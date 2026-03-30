package org.anne.zombideck.data

/**
 * Represents a game expansion.
 *
 * @param prefKey         SharedPreferences key used to persist the enabled state.
 * @param cardRange       The full range of card IDs belonging to this expansion,
 *                        or null if the expansion adds no cards (e.g. Urban Legends).
 * @param easyRange       Sub-range for "easy" cards within this expansion (if applicable).
 * @param hardRange       Sub-range for "hard" cards within this expansion (if applicable).
 * @param extraRange      Sub-range for "extra activation" cards within this expansion (if applicable).
 */
enum class Expansion(
    val prefKey: String,
    val cardRange: IntRange? = null,
    val easyRange: IntRange? = null,
    val hardRange: IntRange? = null,
    val extraRange: IntRange? = null,
) {
    BASE(
        prefKey    = "base",
        cardRange  = 1..40,
        easyRange  = 1..18,
        hardRange  = 19..36,
        extraRange = 37..40,
    ),
    FORT_HENDRIX(
        prefKey    = "fortHendrix",
        cardRange  = 41..80,
        easyRange  = 41..58,
        hardRange  = 59..76,
        extraRange = 77..80,
    ),
    DANNY_TREJO(
        prefKey   = "dannyTrejo",
        cardRange = 81..86,
    ),
    URBAN_LEGENDS(
        prefKey = "urbanLegends",
        // No cards — only adds Abominations.
    );

    /** True if this expansion has difficulty sub-ranges (easy / hard / extra). */
    val hasDifficultyRanges: Boolean
        get() = easyRange != null && hardRange != null && extraRange != null
}