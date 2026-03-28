package org.anne.zombideck.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.anne.zombideck.R

enum class ZombieType(@param:StringRes val nameRes: Int, @param:DrawableRes val imageRes: Int) {
    WALKER(R.string.walker, R.drawable.zombie_walker),
    RUNNER(R.string.runner, R.drawable.zombie_runner),
    FATTY(R.string.fatty, R.drawable.zombie_fatty),
    ABOMINATION(R.string.abomination, R.drawable.abomination_hobomination),
    TREJO(R.string.trejo, R.drawable.zombie_trejo)
}