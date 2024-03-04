package org.anne.zombiedeck.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.anne.zombiedeck.R

enum class ZombieType(@StringRes val nameRes: Int, @DrawableRes val imageRes: Int) {
    WALKER(R.string.walker, R.drawable.zombie_walker),
    RUNNER(R.string.runner, R.drawable.zombie_runner),
    FATTY(R.string.fatty, R.drawable.zombie_fatty),
    ABOMINATION(R.string.abomination, R.drawable.zombie_abomination);
}