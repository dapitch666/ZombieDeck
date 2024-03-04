package org.anne.zombiedeck.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.anne.zombiedeck.R
import org.anne.zombiedeck.data.Abomination
import org.anne.zombiedeck.data.Card
import org.anne.zombiedeck.data.CardType
import org.anne.zombiedeck.data.Danger
import org.anne.zombiedeck.data.ZombieType
import org.anne.zombiedeck.ui.theme.ZombieDeckTheme
import org.anne.zombiedeck.ui.theme.overpassMonoFont

@Composable
fun ZombieCard(
    card: Card?,
    abomination: Abomination?,
    modifier: Modifier = Modifier,
    danger: Danger = Danger.BLUE,
) {
    val isAbomination = abomination != null
    val stripeColor = when {
        isAbomination -> colorResource(R.color.danger_yellow)
        card?.cardType == CardType.RUSH -> colorResource(R.color.danger_yellow)
        card?.cardType == CardType.EXTRA_ACTIVATION -> colorResource(R.color.danger_red)
        else -> colorResource(R.color.white)
    }
    val fontColor = when {
        isAbomination -> colorResource(R.color.danger_yellow)
        card?.cardType == CardType.RUSH -> colorResource(R.color.black)
        else -> colorResource(R.color.white)
    }
    val topBgColor = when {
        isAbomination -> colorResource(R.color.black)
        card?.cardType == CardType.SPAWN -> colorResource(R.color.black)
        card?.cardType == CardType.EXTRA_ACTIVATION -> colorResource(R.color.danger_red)
        card?.cardType == CardType.RUSH -> colorResource(R.color.danger_yellow)
        else -> colorResource(R.color.black)
    }
    val amount = card?.getAmount(danger)
    Card(
        modifier = modifier
            .size(260.dp, 400.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (card != null || abomination != null) {
                Image(
                    painter = painterResource(R.drawable.bg_card),
                    contentDescription = stringResource(id = R.string.not_important),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                // Top of the card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .rotate(-5f)
                            .fillMaxSize()
                            .scale(1.2f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .background(topBgColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .background(
                                    createStripeBrush(
                                        stripeWidth = 10.dp,
                                        stripeBg = stripeColor,
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .alpha(0.5f)
                                .background(colorResource(R.color.black))
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, top = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isAbomination) {
                                stringResource(id = abomination!!.nameRes).uppercase()
                            } else {
                                stringResource(id = card!!.zombieType.nameRes).uppercase()
                            },
                            color = fontColor,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                        )
                        if (!isAbomination) {
                            val cardId = stringResource(
                                R.string.card_number,
                                String.format("%03d", card!!.id)
                            )
                            Text(
                                text = cardId,
                                color = fontColor,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                modifier = Modifier.alpha(0.8f)
                            )
                        }
                    }
                }
                // Zombie image
                Image(
                    painter = painterResource(
                        if (isAbomination) abomination!!.imageRes else card!!.zombieType.imageRes
                    ),
                    contentDescription = stringResource(id = R.string.not_important),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .offset(if (isAbomination) 0.dp else (-30).dp, 20.dp)
                )
                // Zombie count
                // Hidden if the card is an abomination or an extra activation
                if (!isAbomination && card?.cardType != CardType.EXTRA_ACTIVATION) {
                    AnimatedContent(
                        targetState = danger,
                        label = "Danger level change",
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() togetherWith
                                        slideOutVertically { height -> height } + fadeOut()
                            }.using(
                                // Disable clipping since the faded slide-in/out should
                                // be displayed out of bounds.
                                SizeTransform(clip = false)
                            )
                        },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterEnd)
                    ) { targetDanger ->
                        val dangerColor = colorResource(targetDanger.colorRes)
                        Text(
                            text = stringResource(
                                id = R.string.amount,
                                card!!.getAmount(targetDanger)
                            ),
                            color = colorResource(targetDanger.getTextColor()),
                            fontFamily = overpassMonoFont,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .drawBehind {
                                    drawRoundRect(
                                        color = dangerColor,
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
                // Bottom of the card
                if (isAbomination) {
                    Text(
                        text = stringResource(abomination!!.ruleRes),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.black))
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        color = fontColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                } else if (card?.cardType != CardType.SPAWN) {
                    val text = when (card?.cardType) {
                        CardType.EXTRA_ACTIVATION -> {
                            if (amount!! > 0) stringResource(id = R.string.one_extra_activation)
                            else stringResource(id = R.string.no_extra_activation)
                        }

                        CardType.RUSH -> stringResource(id = R.string.spawn_then_activate)
                        else -> ""
                    }
                    Text(
                        text = text,
                        modifier = modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        color = colorResource(id = R.color.white),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.card_back),
                    contentDescription = stringResource(id = R.string.not_important),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.matchParentSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawZombieCardFattyRushPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = Card(
                7,
                CardType.RUSH,
                ZombieType.FATTY,
                listOf(0, 4, 6, 8)
            ),
            abomination = null,
            danger = Danger.BLUE
        )

    }
}

@Preview
@Composable
fun DrawZombieCardRunnerSpawnPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = Card(
                7,
                CardType.SPAWN,
                ZombieType.RUNNER,
                listOf(2, 4, 6, 8)
            ),
            abomination = null,
            danger = Danger.ORANGE
        )

    }
}

@Preview
@Composable
fun DrawZombieCardWalkerExtraPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = Card(
                7,
                CardType.EXTRA_ACTIVATION,
                ZombieType.WALKER,
                listOf(2, 4, 6, 8)
            ),
            abomination = null,
            danger = Danger.YELLOW
        )

    }
}

@Preview
@Composable
fun DrawZombieCardAbominawildPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = null,
            abomination = Abomination.ABOMINAWILD
        )
    }
}

@Preview
@Composable
fun DrawZombieCardPatient0Preview() {
    ZombieDeckTheme {
        ZombieCard(
            card = null,
            abomination = Abomination.PATIENT_0
        )
    }
}

@Preview
@Composable
fun DrawZombieCardAbominacopPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = null,
            abomination = Abomination.ABOMINACOP
        )
    }
}

@Preview
@Composable
fun DrawZombieCardHobominationPreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = null,
            abomination = Abomination.HOBOMINATION
        )
    }
}

@Preview
@Composable
fun DrawZombieCardNonePreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = null,
            abomination = null
        )
    }
}
