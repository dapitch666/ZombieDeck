package org.anne.zombiedeck.data

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class DangerTest {

    private var currentDanger = Danger.BLUE

    @Test
    fun next() {
        assertEquals(Danger.YELLOW, currentDanger.next())
    }

    @Test(expected = IllegalStateException::class)
    fun previous() {
        currentDanger.previous()
    }

    @Test
    fun fromRedToBlue() {
        currentDanger = Danger.RED
        assertEquals(Danger.ORANGE, currentDanger.previous())
        currentDanger = currentDanger.previous()
        assertEquals(Danger.YELLOW, currentDanger.previous())
        currentDanger = currentDanger.previous()
        assertEquals(Danger.BLUE, currentDanger.previous())
    }

    @Test
    fun getTextColor() {
        assertNotNull(currentDanger.getTextColor())
    }
}