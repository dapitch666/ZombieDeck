package org.anne.zombideck.data

data class Card (
    val id: Int,
    val cardType: CardType,
    val zombieType: ZombieType,
    val amount: List<Int>,
    val shooter: Boolean = false
) {
    fun getAmount(danger: Danger): Int {
        return amount.getOrElse(danger.index) { 0 }
    }
    fun isAbomination(): Boolean {
        return zombieType == ZombieType.ABOMINATION
    }

    fun isShooter(): Boolean {
        return shooter
    }
}