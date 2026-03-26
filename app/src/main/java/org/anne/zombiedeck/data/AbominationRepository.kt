package org.anne.zombiedeck.data

import org.anne.zombiedeck.settings.MyPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AbominationRepository @Inject constructor(
    private val myPreference: MyPreference?,
) {
    fun getAvailableAbominations(): List<Abomination> {
        val enabledExpansions = Expansion.entries.filterTo(mutableSetOf()) { expansion ->
            expansion == Expansion.BASE || isExpansionEnabled(expansion)
        }

        val available = Abomination.entries.filter { it.expansion in enabledExpansions }
        if (available.isNotEmpty()) {
            return available
        }

        // Safety fallback: if preferences are corrupted, keep core abominations playable.
        return Abomination.entries.filter { it.expansion == Expansion.BASE }
    }

    private fun isExpansionEnabled(expansion: Expansion): Boolean {
        if (expansion == Expansion.BASE) return true
        return myPreference?.getBoolean(expansion.prefKey, defValue = false) ?: false
    }
}

