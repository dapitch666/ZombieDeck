package org.anne.zombideck.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.anne.zombideck.R

enum class Abomination(
    @param:StringRes val nameRes: Int,
    @param:DrawableRes val imageRes: Int,
    @param:StringRes val ruleRes: Int,
    val expansion: Expansion = Expansion.BASE)
{
    HOBOMINATION(R.string.hobomination, R.drawable.abomination_hobomination, R.string.hobomination_rule),
    ABOMINACOP(R.string.abominacop, R.drawable.abomination_abominacop, R.string.abominacop_rule),
    ABOMINAWILD(R.string.abominawild, R.drawable.abomination_abominawild, R.string.abominawild_rule),
    PATIENT_0(R.string.patient_0, R.drawable.abomination_patient_0, R.string.patient_0_rule),
    ABDUCTOR(R.string.abductor, R.drawable.abomination_abductor, R.string.abductor_rule, Expansion.URBAN_LEGENDS),
    CHUPACABRA(R.string.chupacabra, R.drawable.abomination_chupacabra, R.string.chupacabra_rule, Expansion.URBAN_LEGENDS),
    KILLER_CLOWN(R.string.killer_clown, R.drawable.abomination_killer_clown, R.string.killer_clown_rule, Expansion.URBAN_LEGENDS),
    SEWER_CROCODILE(R.string.sewer_crocodile, R.drawable.abomination_sewer_crocodile, R.string.sewer_crocodile_rule, Expansion.URBAN_LEGENDS);
}