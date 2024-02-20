package org.anne.zombiedeck.ui.components

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
import androidx.compose.ui.text.font.FontFamily
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

@Composable
fun ZombieCard(
    card: Card?,
    abomination: Abomination?,
    modifier: Modifier = Modifier,
    danger: Danger = Danger.BLUE
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
            .size(260.dp, 400.dp)
            .padding(16.dp),
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
                        .height(60.dp)
                ) {
                    Column(
                        modifier
                            .rotate(-5f)
                            .scale(1.2f)
                            .fillMaxSize()
                            .offset(0.dp, (-2).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(49.dp)
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
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val zombieName = if (isAbomination) {
                            stringResource(id = abomination!!.nameRes).uppercase()
                        } else {
                            stringResource(id = card!!.zombieType.nameRes).uppercase()
                        }

                        Text(
                            text = zombieName,
                            color = fontColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                        if (!isAbomination) {
                            val cardId = stringResource(
                                R.string.card_number,
                                String.format("%03d", card!!.id)
                            )
                            Text(
                                text = cardId,
                                color = fontColor,
                                fontSize = 16.sp,
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
                // Hidden if the card is an abomination or an extra activation with 0 activations
                if (!isAbomination && !(card?.cardType == CardType.EXTRA_ACTIVATION && amount!! == 0)) {
                    val rectColor = colorResource(danger.colorRes)
                    Box(
                        modifier = Modifier
                            //.background(colorResource(R.color.black))
                            .align(Alignment.CenterEnd)
                            .padding(16.dp, 8.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = rectColor,
                                    size = size,
                                    cornerRadius = CornerRadius(10.dp.toPx())
                                )
                            }
                    ) {
                        Text(
                            text = stringResource(id = R.string.amount, amount!!),
                            color = colorResource(danger.getTextColor()),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterEnd)
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
                            .padding(16.dp),
                        color = fontColor,
                        fontSize = 12.sp,
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
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.card_back),
                    contentDescription = stringResource(id = R.string.not_important),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawZombieCardZombiePreview() {
    ZombieDeckTheme {
        ZombieCard(
            card = Card(
                21,
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
fun DrawZombieCardAbominationPreview() {
    ZombieDeckTheme {

        ZombieCard(
            card = null,
            abomination = Abomination.ABOMINACOP
        )
    }
}
