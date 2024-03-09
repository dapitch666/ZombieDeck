package org.anne.zombiedeck.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import org.anne.zombiedeck.R
import org.anne.zombiedeck.data.Abomination
import org.anne.zombiedeck.data.Card
import org.anne.zombiedeck.data.CardType
import org.anne.zombiedeck.data.Danger
import org.anne.zombiedeck.data.ZombieType
import org.anne.zombiedeck.ui.components.DangerLevelIconButton
import org.anne.zombiedeck.ui.components.DangerProgressBar
import org.anne.zombiedeck.ui.components.SoundButton
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
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Create references for the composables
        val (
            backgroundImage, soundButton, dangerRow, cardContent,
            abominationButton, previousButton, nextButton, abominationDialog
        ) = createRefs()
        // Local state for the abomination dialog
        var showAbominationDialog by remember { mutableStateOf(false) }
        val interactionSource = remember { MutableInteractionSource() }

        // Background image
        Image(
            painter = painterResource(id = R.drawable.bg_sheet),
            contentDescription = "Background image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.constrainAs(backgroundImage) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        // Sound button
        SoundButton(
            onClick = { toggleMute() },
            enabled = !isMuted,
            modifier = Modifier.constrainAs(soundButton) {
                top.linkTo(parent.top, margin = 92.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )
        // Danger level row
        Row(
            modifier = Modifier.constrainAs(dangerRow) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isFirstDangerLevel = danger == Danger.BLUE
            val isLastDangerLevel = danger == Danger.RED
            DangerLevelIconButton(
                onClick = decreaseDangerLevel,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                color = if (isFirstDangerLevel) R.color.scrimColor else danger.previous().colorRes,
                enabled = !isFirstDangerLevel,
                description = R.string.decrease_danger_level
            )
            DangerProgressBar(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                currentProgress = progress,
                danger = danger,
            )
            DangerLevelIconButton(
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                color = if (isLastDangerLevel) R.color.scrimColor else danger.next().colorRes,
                onClick = increaseDangerLevel,
                enabled = !isLastDangerLevel,
                description = R.string.increase_danger_level
            )
        }
        // Card
        AnimatedContent(
            targetState = card,
            modifier = Modifier.constrainAs(cardContent) {
                top.linkTo(dangerRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(abominationButton.top)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
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

        // Action buttons
        // Draw / see abomination button
        val amount = card?.getAmount(danger) ?: 0
        val drawNewAbomination = card?.isAbomination() == true && amount > 0
        if(!isMuted && drawNewAbomination && !abominationJustDrawn) playAbominationSound()
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
            modifier = Modifier.constrainAs(abominationButton) {
                bottom.linkTo(previousButton.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
        )
        ZombieButton(
            buttonText = stringResource(id = R.string.previous_card),
            onClick = { previousCard() },
            enable = !isFirstCard,
            fillWidth = true,
            modifier = Modifier.constrainAs(previousButton) {
                bottom.linkTo(parent.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(nextButton.start, margin = 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )
        ZombieButton(
            buttonText = if (isLastCard) stringResource(id = R.string.shuffle)
            else stringResource(id = R.string.draw_a_card),
            onClick = { nextCard() },
            fillWidth = true,
            modifier = Modifier.constrainAs(nextButton) {
                bottom.linkTo(parent.bottom, margin = 32.dp)
                start.linkTo(previousButton.end, margin = 8.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        // Abomination dialog
        AnimatedVisibility(
            visible = showAbominationDialog,
            label = "Abomination card",
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .constrainAs(abominationDialog) {
                    top.linkTo(cardContent.top)
                    start.linkTo(cardContent.start)
                    end.linkTo(cardContent.end)
                    bottom.linkTo(cardContent.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                .zIndex(2f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { showAbominationDialog = false }
                )
        ) {
            ZombieCard(
                card = null,
                abomination = abomination,
            )
        }

        // Dim background when abomination dialog is shown
        AnimatedVisibility(
            visible = showAbominationDialog,
            label = "Abomination dialog background",
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.background(color = Color.Black.copy(alpha = 0.6f), RectangleShape)
                    .zIndex(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { showAbominationDialog = false }
                    )
            )
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
            isMuted = false
        )
    }
}
