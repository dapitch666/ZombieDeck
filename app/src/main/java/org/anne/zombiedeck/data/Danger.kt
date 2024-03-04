package org.anne.zombiedeck.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import org.anne.zombiedeck.R

enum class Danger(val index: Int, @StringRes val nameRes: Int, @ColorRes val colorRes: Int) {
    BLUE(0, R.string.blue, R.color.danger_blue),
    YELLOW(1, R.string.yellow, R.color.danger_yellow),
    ORANGE(2, R.string.orange, R.color.danger_orange),
    RED(3, R.string.red, R.color.danger_red);

    fun next(): Danger {
        return when (this) {
            BLUE -> YELLOW
            YELLOW -> ORANGE
            ORANGE -> RED
            RED -> throw IllegalStateException("There is no next danger")
        }
    }

    fun previous(): Danger {
        return when (this) {
            BLUE -> throw IllegalStateException("There is no previous danger")
            YELLOW -> BLUE
            ORANGE -> YELLOW
            RED -> ORANGE
        }
    }

    fun getTextColor(): Int {
        return if (this == YELLOW) R.color.black else R.color.white
    }
}