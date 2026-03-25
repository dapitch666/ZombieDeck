package org.anne.zombiedeck.data

import org.junit.Assert.assertEquals
import org.junit.Test

class CardsDataTest {
    @Test
    fun allCards_isCompleteAndUnique() {
        assertEquals("Should have 86 cards", 86, allCards.size)
        val ids = allCards.map { it.id }
        assertEquals("All IDs should be unique", ids.distinct().size, ids.size)
        assertEquals("IDs should range from 1 to 86", (1..86).toList(), ids.sorted())
    }

    @Test
    fun allCards_checkZombiesTypes() {
        assertEquals(36, allCards.count { it.zombieType == ZombieType.WALKER })
        assertEquals(18, allCards.count { it.zombieType == ZombieType.FATTY })
        assertEquals(18, allCards.count { it.zombieType == ZombieType.RUNNER })
        assertEquals(8, allCards.count { it.zombieType == ZombieType.ABOMINATION })
        assertEquals(6, allCards.count { it.zombieType == ZombieType.TREJO })
    }

    @Test
    fun allCards_shooters() {
        assertEquals(14, allCards.count { it.isShooter() })
    }
}