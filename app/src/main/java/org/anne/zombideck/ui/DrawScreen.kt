package org.anne.zombideck.ui

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.anne.zombideck.R
import org.anne.zombideck.data.Abomination
import org.anne.zombideck.data.Card
import org.anne.zombideck.data.CardType
import org.anne.zombideck.data.Danger
import org.anne.zombideck.data.ZombieType
import org.anne.zombideck.ui.components.DangerLevelIconButton
import org.anne.zombideck.ui.components.DangerProgressBar
import org.anne.zombideck.ui.components.SoundButton
import org.anne.zombideck.ui.components.ZombieButton
import org.anne.zombideck.ui.components.ZombieCard
import org.anne.zombideck.ui.theme.ZombiDeckTheme
import org.anne.zombideck.viewmodels.GameViewModel

@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    playAbominationSound: () -> Unit = {},
    playShooterSound: () -> Unit = {},
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
        playShooterSound = playShooterSound,
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
    decreaseDangerLevel: () -> Unit = {},
    increaseDangerLevel: () -> Unit = {},
    progress: Float = 0f,
    abominationJustDrawn: Boolean = false,
    drawAbomination: () -> Unit = {},
    isFirstCard: Boolean = false,
    isLastCard: Boolean = false,
    previousCard: () -> Unit = {},
    nextCard: () -> Unit = {},
    playAbominationSound: () -> Unit = {},
    playShooterSound: () -> Unit = {},
    isMuted: Boolean = false,
    toggleMute: () -> Unit = {},
    showAbominationDialog: Boolean? = null,
) {
    // Responsive dimensions based on current window container size.
    val density = LocalDensity.current
    val windowSize = LocalWindowInfo.current.containerSize
    val screenHeightDp = with(density) { windowSize.height.toDp().value.toInt() }
    val screenWidthDp = with(density) { windowSize.width.toDp().value.toInt() }

    // Small layouts move some controls downward to avoid card overlap.
    val isSmallScreen = screenHeightDp < 700

    // Breakpoints tuned from previews to keep card/actions visible on short devices.
    val topMarginDanger = when {
        screenHeightDp < 600 -> 12.dp
        screenHeightDp < 700 -> 16.dp
        screenHeightDp < 800 -> 24.dp
        else -> 32.dp
    }
    val topMarginSound = when {
        screenHeightDp < 600 -> 56.dp
        screenHeightDp < 700 -> 68.dp
        screenHeightDp < 800 -> 80.dp
        else -> 92.dp
    }
    val cardTopMargin = when {
        screenHeightDp < 600 -> 8.dp
        screenHeightDp < 700 -> 12.dp
        else -> 16.dp
    }
    val cardWidthPercent = when {
        // Smaller card on short displays to avoid vertical overlap with controls.
        screenHeightDp < 600 && screenWidthDp < 384 -> 0.56f
        screenHeightDp < 600 -> 0.58f
        screenHeightDp < 700 && screenWidthDp < 384 -> 0.62f
        screenHeightDp < 700 -> 0.64f
        screenWidthDp < 384 -> 0.8f
        else -> 0.7f
    }
    val cardBottomMargin = when {
        screenHeightDp < 600 -> 8.dp
        screenHeightDp < 700 -> 12.dp
        else -> 16.dp
    }
    val buttonSpacing = when {
        screenHeightDp < 600 -> 4.dp
        screenHeightDp < 700 -> 6.dp
        else -> 8.dp
    }
    val bottomMarginButtons = when {
        screenHeightDp < 600 -> 16.dp
        screenHeightDp < 700 -> 20.dp
        screenHeightDp < 800 -> 24.dp
        else -> 32.dp
    }
    val abominationButtonMargin = when {
        screenHeightDp < 600 -> 16.dp
        screenHeightDp < 700 -> 20.dp
        else -> 32.dp
    }
    val horizontalMargin = when {
        screenWidthDp < 384 -> 8.dp
        else -> 16.dp
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Create references for the composables
        val (
            backgroundImage, soundButton, dangerRow, cardContent,
            abominationButton, previousButton, nextButton, abominationDialog
        ) = createRefs()
        // Local state is used in runtime; previews can force visibility via parameter.
        var internalShowAbominationDialog by remember { mutableStateOf(false) }
        val isAbominationDialogVisible = showAbominationDialog ?: internalShowAbominationDialog
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
                if (isSmallScreen) {
                    // On small screens, align to the right of the abomination button
                    bottom.linkTo(previousButton.top, margin = abominationButtonMargin)
                    start.linkTo(abominationButton.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = horizontalMargin)
                } else {
                    // On larger screens, keep at top right
                    top.linkTo(parent.top, margin = topMarginSound)
                    end.linkTo(parent.end, margin = horizontalMargin)
                }
            }
        )
        // Danger level row
        Row(
            modifier = Modifier.constrainAs(dangerRow) {
                top.linkTo(parent.top, margin = topMarginDanger)
                start.linkTo(parent.start, margin = horizontalMargin)
                end.linkTo(parent.end, margin = horizontalMargin)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isFirstDangerLevel = danger == Danger.BLUE
            val isLastDangerLevel = danger == Danger.RED
            DangerLevelIconButton(
                onClick = { decreaseDangerLevel() },
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
                onClick = { increaseDangerLevel() },
                enabled = !isLastDangerLevel,
                description = R.string.increase_danger_level
            )
        }
        // Card
        AnimatedContent(
            targetState = card,
            modifier = Modifier.constrainAs(cardContent) {
                top.linkTo(dangerRow.bottom, margin = cardTopMargin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(abominationButton.top, margin = cardBottomMargin)
                width = Dimension.percent(cardWidthPercent)
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
        val isAbominationCard = card?.isAbomination() == true && amount > 0
        val isShooterCard = card?.isShooter() == true

        LaunchedEffect(card, isAbominationCard, isShooterCard, isForward, abominationJustDrawn, isMuted) {
            // Abomination sound plays once when a drawable abomination card appears.
            if (!isMuted && isAbominationCard && !abominationJustDrawn) {
                playAbominationSound()
            }
            // Shooter cue is tied to forward draws, not backward navigation.
            if (!isMuted && isShooterCard && isForward) {
                playShooterSound()
            }
        }

        // Button is enabled when an abomination can be drawn or already exists.
        val enable = isAbominationCard || abomination != null

        ZombieButton(
            buttonText = if (isAbominationCard && !abominationJustDrawn) {
                stringResource(id = R.string.draw_an_abomination)
            } else {
                stringResource(id = R.string.see_abomination)
            },
            onClick = {
                if (isAbominationCard && !abominationJustDrawn) {
                    drawAbomination()
                }
                // In previews/tests, an explicit param can fully control dialog visibility.
                if (showAbominationDialog == null) {
                    internalShowAbominationDialog = internalShowAbominationDialog.not()
                }
            },
            enable = enable,
            modifier = Modifier.constrainAs(abominationButton) {
                bottom.linkTo(previousButton.top, margin = abominationButtonMargin)
                start.linkTo(parent.start, margin = horizontalMargin)
                end.linkTo(parent.end, margin = horizontalMargin)

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
                bottom.linkTo(parent.bottom, margin = bottomMarginButtons)
                start.linkTo(parent.start, margin = horizontalMargin)
                end.linkTo(nextButton.start, margin = buttonSpacing)
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
                bottom.linkTo(parent.bottom, margin = bottomMarginButtons)
                start.linkTo(previousButton.end, margin = buttonSpacing)
                end.linkTo(parent.end, margin = horizontalMargin)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        // Abomination dialog
        AnimatedVisibility(
            visible = isAbominationDialogVisible,
            label = "Abomination card",
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .constrainAs(abominationDialog) {
                    top.linkTo(cardContent.top)
                    start.linkTo(cardContent.start)
                    end.linkTo(cardContent.end)
                    bottom.linkTo(cardContent.bottom)
                    width = Dimension.percent(0.9f)
                    height = Dimension.wrapContent
                }
                .zIndex(2f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        if (showAbominationDialog == null) {
                            internalShowAbominationDialog = false
                        }
                    }
                )
        ) {
            ZombieCard(
                card = null,
                abomination = abomination,
            )
        }

        // Dim layer is rendered under the dialog card and closes on outside tap.
        AnimatedVisibility(
            visible = isAbominationDialogVisible,
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
                        onClick = {
                            if (showAbominationDialog == null) {
                                internalShowAbominationDialog = false
                            }
                        }
                    )
            )
        }
    }
}

@Preview(name = "Small phone", locale = "fr", device = "id:small_phone", showSystemUi = true) // spec:width=360dp,height=640dp,dpi=240
@Preview(name = "Medium phone", device = "id:medium_phone", showSystemUi = true)
@Preview(name = "A56 French", locale = "fr", device = "spec:width=384dp,height=832dp,dpi=450", showSystemUi = true)
@Composable
fun DrawScreenPreview() {
    ZombiDeckTheme {
        DrawUIScreen(
            card = Card(
                72,
                CardType.SPAWN,
                ZombieType.FATTY,
                listOf(0, 4, 6, 8),
                shooter = true
            ),
            abomination = Abomination.ABOMINAWILD,
            danger = Danger.YELLOW,
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

@Preview(locale = "fr")
@Composable
fun DrawScreenPreviewFrench() {
    ZombiDeckTheme {
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
            abominationJustDrawn = true,
            drawAbomination = {},
            isFirstCard = false,
            isLastCard = true,
            previousCard = {},
            nextCard = {},
            isMuted = true
        )
    }
}

@Preview(name = "Abomination dialog open")
@Composable
fun DrawScreenPreviewAbominationDialogOpen() {
    ZombiDeckTheme {
        DrawUIScreen(
            card = Card(
                57,
                CardType.SPAWN,
                ZombieType.ABOMINATION,
                listOf(1, 1, 1, 1)
            ),
            abomination = Abomination.ABOMINACOP,
            danger = Danger.BLUE,
            decreaseDangerLevel = {},
            increaseDangerLevel = {},
            progress = 0.7f,
            abominationJustDrawn = true,
            drawAbomination = {},
            isFirstCard = false,
            isLastCard = false,
            previousCard = {},
            nextCard = {},
            isMuted = false,
            showAbominationDialog = true
        )
    }
}
