package org.anne.zombiedeck.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.anne.zombiedeck.R

enum class Abomination(@StringRes val nameRes: Int, @DrawableRes val imageRes: Int, @StringRes val ruleRes: Int) {
    HOBOMINATION(R.string.hobomination, R.drawable.abomination_hobomination, R.string.hobomination_rule),
    ABOMINACOP(R.string.abominacop, R.drawable.abomination_abominacop, R.string.abominacop_rule),
    ABOMINAWILD(R.string.abominawild, R.drawable.abomination_abominawild, R.string.abominawild_rule),
    PATIENT_0(R.string.patient_0, R.drawable.abomination_patient_0, R.string.patient_0_rule);
}