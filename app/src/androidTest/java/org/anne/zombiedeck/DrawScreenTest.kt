package org.anne.zombiedeck

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DrawScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var activity: MainActivity

    @Before
    fun init() {
        hiltRule.inject()

        // Start the application
        composeTestRule.activityRule.scenario.onActivity {
            activity = it
        }

        // Go to the draw screen
        composeTestRule.onNodeWithStringId(R.string.shuffle_and_start)
            .assertExists()
            .performClick()
    }

    @Test
    fun drawScreenLoadingTest() {
        composeTestRule.onRoot().printToLog("drawScreenLoadingTest")
        // Check that the buttons are disabled
        composeTestRule.onNodeWithStringId(R.string.see_abomination)
            .assertExists()
            .assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.previous_card)
            .assertExists()
            .assertIsNotEnabled()

        // No card was drawn yet, so the card back should be visible
        composeTestRule.onNodeWithContentDescription("Card back")
            .assertExists()

        // Draw first card
        composeTestRule.onNodeWithStringId(R.string.draw_a_card)
            .assertExists()
            .assertIsEnabled()
            .performClick()

        // Card back should be replaced by the card front
        composeTestRule.onNodeWithContentDescription("Card back")
            .assertDoesNotExist()
        composeTestRule.onNode(
            hasText("FATTY") or hasText("RUNNER") or hasText("WALKER") or hasText("ABOMINATION")
        ).assertExists()

    }

    /*
     * Testing the previous card button
     * The previous card button should be disabled until a second card is drawn
     */
    @Test
    fun testPreviousCardButton() {
        // Draw first card
        composeTestRule.onNodeWithStringId(R.string.draw_a_card)
            .assertExists()
            .assertIsEnabled()
            .performClick()

        // The previous card button should be disabled
        composeTestRule.onNodeWithStringId(R.string.previous_card)
            .assertExists()
            .assertIsNotEnabled()

        // Draw second card
        composeTestRule.onNodeWithStringId(R.string.draw_a_card)
            .performClick()

        // The previous card button should be enabled. Click on it.
        composeTestRule.onNodeWithStringId(R.string.previous_card)
            .assertExists()
            .assertIsEnabled()
            .performClick()

        // The previous card button should be disabled again
        composeTestRule.onNodeWithStringId(R.string.previous_card)
            .assertIsNotEnabled()
    }

    /*
     * Testing the sound button
     */
    @Test
    fun testSoundButton() {
        composeTestRule.onNodeWithContentDescription("Toggle sound on/off")
            .printToLog("testSoundButton")

        composeTestRule.onNodeWithContentDescription("Toggle sound on/off")
            .assertExists()
            .assertIsEnabled()
            .performClick()
    }

    /*
     * Testing the danger level buttons
     * The danger level should be increased and decreased when the buttons are clicked
     *
     */
    @Test
    fun testDangerLevel() {
        composeTestRule.onNodeWithText("blue danger level")
            .assertExists()

        composeTestRule.onNodeWithDescriptionStringId(R.string.decrease_danger_level)
            .assertExists()
            .assertIsNotEnabled()

        composeTestRule.onNodeWithDescriptionStringId(R.string.increase_danger_level)
            .assertExists()
            .assertIsEnabled()
            .performClick()

        composeTestRule.onNodeWithText("yellow danger level")
            .assertExists()
        composeTestRule.onNodeWithText("blue danger level")
            .assertDoesNotExist()

        composeTestRule.onNodeWithDescriptionStringId(R.string.increase_danger_level)
            .performClick()

        composeTestRule.onNodeWithText("orange danger level")
            .assertExists()

        composeTestRule.onNodeWithDescriptionStringId(R.string.increase_danger_level)
            .performClick()

        composeTestRule.onNodeWithText("red danger level")
            .assertExists()

        composeTestRule.onNodeWithDescriptionStringId(R.string.increase_danger_level)
            .assertIsNotEnabled()

        composeTestRule.onNodeWithDescriptionStringId(R.string.decrease_danger_level)
            .assertExists()
            .assertIsEnabled()
            .performClick()

        composeTestRule.onNodeWithText("orange danger level")
            .assertExists()
    }
}
