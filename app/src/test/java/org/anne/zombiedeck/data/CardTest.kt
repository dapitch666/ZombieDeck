package org.anne.zombiedeck.data

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CardTest {
    private val walker = allCards[0]
    private val abomination = allCards[17]

    @Test
    fun getAmountTest() {
        assertEquals(1, walker.getAmount(Danger.BLUE))
        assertEquals(4, walker.getAmount(Danger.ORANGE))
    }

    @Test
    fun isAbomination() {
        assertTrue(abomination.isAbomination())
        assertFalse(walker.isAbomination())
    }
}