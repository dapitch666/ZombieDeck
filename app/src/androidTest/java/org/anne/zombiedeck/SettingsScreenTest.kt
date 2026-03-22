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
import androidx.compose.ui.test.onAllNodesWithText
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
    companion object {
        private const val EASY_TAG = "easy"
        private const val HARD_TAG = "hard"
        private const val EXTRA_TAG = "extra"
        private const val FORT_HENDRIX_TAG = "fort_hendrix"
    }

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
                playAbominationSound = { },
                playShooterSound = { }
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
        composeTestRule.onNodeWithTag(EASY_TAG)
            .assertExists()
            .assertIsDisplayed()
            .assertIsOn()
            .performClick()
            .assertIsOff()

        composeTestRule.onNodeWithTag(HARD_TAG)
            .assertExists()
            .assertIsDisplayed()
            .assertIsOn()
            .performClick()
            .assertIsOff()

        composeTestRule.onNodeWithTag(EXTRA_TAG)
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

        composeTestRule.onNodeWithTag(EASY_TAG)
            .assertIsOn()

        // The dialog should no longer be displayed
        composeTestRule.onNodeWithStringId(R.string.preferences_alert)
            .assertDoesNotExist()

        // The other two switches should still be off. Turn them all again.
        composeTestRule.onNodeWithTag(HARD_TAG)
            .assertIsOff()
            .performClick()
            .assertIsOn()

        composeTestRule.onNodeWithTag(EXTRA_TAG)
            .assertIsOff()
            .performClick()
            .assertIsOn()
    }

    @Test
    fun testCardLabelsUpdateWhenFortHendrixToggles() {
        // Force a deterministic baseline: Fort Hendrix OFF -> labels 1..40 visible.
        val fortHendrixFirstRangeLabel = composeTestRule.activity.getString(R.string.cards_range, 41, 58)
        val isFortHendrixEnabled = composeTestRule
            .onAllNodesWithText(fortHendrixFirstRangeLabel)
            .fetchSemanticsNodes().isNotEmpty()

        if (isFortHendrixEnabled) {
            composeTestRule.onNodeWithTag(FORT_HENDRIX_TAG)
                .assertExists()
                .performClick()
        }

        composeTestRule.onNodeWithStringId(R.string.cards_range, 1, 18)
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cards_range, 19, 36)
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cards_range, 37, 40)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(FORT_HENDRIX_TAG)
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithStringId(R.string.cards_range, 41, 58)
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cards_range, 59, 76)
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cards_range, 77, 80)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(FORT_HENDRIX_TAG)
            .performClick()

        composeTestRule.onNodeWithStringId(R.string.cards_range, 1, 18)
            .assertExists()
            .assertIsDisplayed()
    }


    @Test
    fun testSwitchesExistAndAreOn() {
        val mySwitches = SemanticsMatcher.expectValue(
            SemanticsProperties.Role, Role.Switch
        )
        composeTestRule.onAllNodes(mySwitches)
            .assertCountEquals(4)
            .assertAll(isOn())
    }
}