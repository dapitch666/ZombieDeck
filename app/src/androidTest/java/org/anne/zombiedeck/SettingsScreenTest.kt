package org.anne.zombiedeck

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.isOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.activity.setContent {
            ZombieDeckApp(
                navController = rememberNavController(),
                playAbominationSound = { }
            )
        }

        // Go to the settings screen
        composeTestRule.onNodeWithStringId(R.string.configure_the_deck)
            .assertExists()
            .performClick()
    }

    @Test
    fun testDialogOpensOnAllSwitchesOff() {
        // Switches are on by default, ensure that they are all on after the test
        composeTestRule.onNodeWithTag("Include cards 1 to 18")
            .assertExists()
            .assertIsDisplayed()
            .assertIsOn()
            .performClick()
            .assertIsOff()

        composeTestRule.onNodeWithTag("Include cards 19 to 36")
            .assertExists()
            .assertIsDisplayed()
            .assertIsOn()
            .performClick()
            .assertIsOff()

        composeTestRule.onNodeWithTag("Include cards 37 to 40")
            .assertExists()
            .assertIsDisplayed()
            .assertIsOn()
            .performClick()
            .assertIsOff()

        // All three switches are off, so the dialog should be displayed
        composeTestRule.onNodeWithStringId(R.string.preferences_alert)
            .assertExists()
            .assertIsDisplayed()

        // Clicking the OK button should dismiss the dialog and toggle the first switch back on
        composeTestRule.onNodeWithStringId(android.R.string.ok)
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("Include cards 1 to 18")
            .assertIsOn()

        // The dialog should no longer be displayed
        composeTestRule.onNodeWithStringId(R.string.preferences_alert)
            .assertDoesNotExist()

        // The other two switches should still be off. Turn them all again.
        composeTestRule.onNodeWithTag("Include cards 19 to 36")
            .assertIsOff()
            .performClick()
            .assertIsOn()

        composeTestRule.onNodeWithTag("Include cards 37 to 40")
            .assertIsOff()
            .performClick()
            .assertIsOn()
    }


    @Test
    fun testSwitchesExistAndAreOn() {
        val mySwitches = SemanticsMatcher.expectValue(
            SemanticsProperties.Role, Role.Switch
        )
        composeTestRule.onAllNodes(mySwitches)
            .assertCountEquals(3)
            .assertAll(isOn())
    }
}