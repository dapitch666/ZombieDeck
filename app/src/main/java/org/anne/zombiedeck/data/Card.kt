package org.anne.zombiedeck.data

data class Card (
    val id: Int,
    val cardType: CardType,
    val zombieType: ZombieType,
    val amount: List<Int>,
) {
    fun getAmount(danger: Danger): Int {
        return amount[danger.index]
    }
    fun isAbomination(): Boolean {
        return zombieType == ZombieType.ABOMINATION
    }
}