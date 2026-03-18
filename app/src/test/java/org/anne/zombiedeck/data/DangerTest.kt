package org.anne.zombiedeck.data

import org.anne.zombiedeck.R
import org.junit.Assert.assertEquals
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
        assertEquals(R.color.white, Danger.BLUE.getTextColor())
        assertEquals(R.color.black, Danger.YELLOW.getTextColor())
        assertEquals(R.color.white, Danger.ORANGE.getTextColor())
        assertEquals(R.color.white, Danger.RED.getTextColor())
    }
}