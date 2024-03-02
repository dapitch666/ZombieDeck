package org.anne.zombiedeck.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import org.anne.zombiedeck.R
import org.anne.zombiedeck.data.Abomination
import org.anne.zombiedeck.data.Card
import org.anne.zombiedeck.data.CardType
import org.anne.zombiedeck.data.Danger
import org.anne.zombiedeck.data.ZombieType
import org.anne.zombiedeck.ui.components.DangerLevelIconButton
import org.anne.zombiedeck.ui.components.DangerProgressBar
import org.anne.zombiedeck.ui.components.ZombieButton
import org.anne.zombiedeck.ui.components.ZombieCard
import org.anne.zombiedeck.ui.theme.ZombieDeckTheme
import org.anne.zombiedeck.viewmodels.GameViewModel

@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    playAbominationSound: () -> Unit = {},
) {
    val gameViewModel: GameViewModel = hiltViewModel()
    val gameUiState by gameViewModel.uiState.collectAsState()
    val isMuted by gameViewModel.isMuted.collectAsState()

    DrawUIScreen(
        modifier = modifier,
        card = gameUiState.currentCard,
        isForward = gameViewModel.isForward(),
        abomination = gameUiState.currentAbomination,
        danger = gameUiState.currentDanger,
        decreaseDangerLevel = { gameViewModel.decreaseDangerLevel() },
        increaseDangerLevel = { gameViewModel.increaseDangerLevel() },
        progress = gameViewModel.getProgress(),
        abominationJustDrawn = gameUiState.abominationJustDrawn,
        drawAbomination = gameViewModel::drawNewAbomination,
        isFirstCard = gameViewModel.isFirstCard(),
        isLastCard = gameViewModel.isLastCard(),
        previousCard = gameViewModel::previousCard,
        nextCard = gameViewModel::nextCard,
        playAbominationSound = playAbominationSound,
        isMuted = isMuted,
        toggleMute = gameViewModel::toggleMute,
    )
}

@Composable
fun DrawUIScreen(
    modifier: Modifier = Modifier,
    card: Card?,
    isForward: Boolean = true,
    abomination: Abomination?,
    danger: Danger = Danger.BLUE,
    decreaseDangerLevel: (Boolean) -> Unit = {},
    increaseDangerLevel: (Boolean) -> Unit = {},
    progress: Float = 0f,
    abominationJustDrawn: Boolean = false,
    drawAbomination: () -> Unit = {},
    isFirstCard: Boolean = false,
    isLastCard: Boolean = false,
    previousCard: () -> Unit = {},
    nextCard: () -> Unit = {},
    playAbominationSound: () -> Unit = {},
    isMuted: Boolean = false,
    toggleMute: () -> Unit = {},
) {
    // Background image
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_sheet),
            contentDescription = stringResource(id = R.string.not_important),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        // Sound control button
        val soundIcon = if (isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_on
        IconButton(
            onClick = { toggleMute() },
            enabled = true,
            content = {
                Image(
                    painter = painterResource(id = soundIcon),
                    contentDescription = stringResource(R.string.toggle_sound_on_off),
                )
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 92.dp, end = 16.dp)
        )
    }
    // Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Danger level buttons and progress bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isFirstDangerLevel = danger == Danger.BLUE
            val isLastDangerLevel = danger == Danger.RED
            DangerLevelIconButton(
                onClick = decreaseDangerLevel,
                icon = Icons.Outlined.KeyboardArrowLeft,
                color = if (isFirstDangerLevel) R.color.grey else danger.previous().colorRes,
                enabled = !isFirstDangerLevel
            )
            DangerProgressBar(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                currentProgress = progress,
                danger = danger,
            )
            DangerLevelIconButton(
                icon = Icons.Outlined.KeyboardArrowRight,
                color = if (isLastDangerLevel) R.color.grey else danger.next().colorRes,
                onClick = increaseDangerLevel,
                enabled = !isLastDangerLevel
            )
        }
        // Card
        AnimatedContent(
            targetState = card,
            label = "Card animation",
            transitionSpec = {
                if (isForward) {
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                } else {
                    slideInHorizontally { width -> -width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> width } + fadeOut()
                }.using(SizeTransform(clip = false))
            }
        ) { targetCard ->
            ZombieCard(
                card = targetCard,
                abomination = null,
                danger = danger,
            )
        }

        // Abomination dialog window
        var showAbominationDialog by remember { mutableStateOf(false) }
        if (showAbominationDialog) {
            Dialog(
                onDismissRequest = { showAbominationDialog = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = (-44).dp)
                        .clickable(onClick = { showAbominationDialog = false })
                ) {
                    ZombieCard(
                        card = null,
                        abomination = abomination,
                    )
                }
            }
        }

        // Action buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Draw / see abomination button
            val amount = card?.getAmount(danger) ?: 0
            val drawNewAbomination = card?.isAbomination() == true && amount > 0
            if (!isMuted && drawNewAbomination && !abominationJustDrawn) {
                playAbominationSound()
            }
            val enable = drawNewAbomination || abomination != null

            ZombieButton(
                buttonText = if (drawNewAbomination && !abominationJustDrawn) {
                    stringResource(id = R.string.draw_an_abomination)
                } else {
                    stringResource(id = R.string.see_abomination)
                },
                onClick = {
                    if (drawNewAbomination && !abominationJustDrawn) {
                        drawAbomination()
                    }
                    showAbominationDialog = showAbominationDialog.not()
                },
                enable = enable,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Previous / next card buttons
                ZombieButton(
                    buttonText = stringResource(id = R.string.previous_card),
                    onClick = { previousCard() },
                    enable = !isFirstCard,
                    modifier = Modifier.weight(1f),
                    fillWidth = true
                )
                ZombieButton(
                    buttonText = if (isLastCard) stringResource(id = R.string.shuffle)
                        else stringResource(id = R.string.draw_a_card),
                    onClick = { nextCard() },
                    modifier = Modifier.weight(1f),
                    fillWidth = true
                )
            }
        }
    }
}


@Preview
@Composable
fun DrawScreenPreview() {
    ZombieDeckTheme {
        DrawUIScreen(
            card = Card(
                21,
                CardType.EXTRA_ACTIVATION,
                ZombieType.FATTY,
                listOf(0, 4, 6, 8)
            ),
            abomination = Abomination.ABOMINAWILD,
            danger = Danger.BLUE,
            decreaseDangerLevel = {},
            increaseDangerLevel = {},
            progress = 0.5f,
            abominationJustDrawn = false,
            drawAbomination = {},
            isFirstCard = true,
            isLastCard = false,
            previousCard = {},
            nextCard = {},
            isMuted = true
        )
    }
}