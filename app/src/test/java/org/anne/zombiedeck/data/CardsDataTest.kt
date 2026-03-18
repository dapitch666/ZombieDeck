package org.anne.zombiedeck.data

import org.junit.Assert.assertEquals
import org.junit.Test

class CardsDataTest {
    @Test
    fun allCards_isCompleteAndUnique() {
        assertEquals("Should have 40 cards", 40, allCards.size)
        val ids = allCards.map { it.id }
        assertEquals("All IDs should be unique", ids.distinct().size, ids.size)
        assertEquals("IDs should range from 1 to 40", (1..40).toList(), ids.sorted())
    }

    @Test
    fun allCards_checkZombiesTypes() {
        assertEquals(18, allCards.count { it.zombieType == ZombieType.WALKER })
        assertEquals(9, allCards.count { it.zombieType == ZombieType.FATTY })
        assertEquals(9, allCards.count { it.zombieType == ZombieType.RUNNER })
        assertEquals(4, allCards.count { it.zombieType == ZombieType.ABOMINATION })
    }
}