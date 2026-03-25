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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
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
import org.anne.zombiedeck.ui.theme.sonoMonoFont
import java.util.Locale

@Composable
fun ZombieCard(
    card: Card?,
    abomination: Abomination?,
    modifier: Modifier = Modifier,
    danger: Danger = Danger.BLUE,
) {
    val isAbomination = abomination != null

    Card(
        modifier = modifier
            .aspectRatio(260f / 400f),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // If no data is provided, show the generic card back.
            if (card == null && abomination == null) {
                ZombieCardBack()
                return@Box
            }

            // Resolve style colors once so all sections stay visually consistent.
            val stripeColor = colorResource(stripeColorRes(card?.cardType, isAbomination))
            val fontColor = colorResource(fontColorRes(card?.cardType, isAbomination))
            val topBgColor = colorResource(topBackgroundRes(card?.cardType, isAbomination))

            // Layer order: static background -> header -> artwork -> overlays.
            ZombieCardFrontBackground()
            ZombieCardTop(
                card = card,
                abomination = abomination,
                isAbomination = isAbomination,
                fontColor = fontColor,
                stripeColor = stripeColor,
                topBgColor = topBgColor,
            )
            ZombieCardImage(card = card, abomination = abomination, isAbomination = isAbomination)

            // Count is intentionally hidden for Trejo and Extra Activation cards.
            if (shouldShowAmount(card = card, isAbomination = isAbomination)) {
                ZombieAmount(
                    card = card,
                    danger = danger,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterEnd)
                )
            }

            // Shooter badge is an additional overlay, independent of card type visuals.
            if (card?.isShooter() == true) {
                ShooterBadge()
            }

            ZombieCardBottom(
                card = card,
                abomination = abomination,
                danger = danger,
                fontColor = fontColor,
            )
        }
    }
}

// Stripe color communicates special behaviors at a glance.
private fun stripeColorRes(cardType: CardType?, isAbomination: Boolean): Int = when {
    isAbomination -> R.color.danger_yellow
    cardType == CardType.RUSH -> R.color.danger_yellow
    cardType == CardType.EXTRA_ACTIVATION -> R.color.danger_red
    else -> R.color.white
}

// Header text color follows card prominence and readability.
private fun fontColorRes(cardType: CardType?, isAbomination: Boolean): Int = when {
    isAbomination -> R.color.danger_yellow
    cardType == CardType.RUSH -> R.color.black
    else -> R.color.white
}

// Header background encodes the primary card action.
private fun topBackgroundRes(cardType: CardType?, isAbomination: Boolean): Int = when {
    isAbomination -> R.color.black
    cardType == CardType.EXTRA_ACTIVATION -> R.color.danger_red
    cardType == CardType.RUSH -> R.color.danger_yellow
    else -> R.color.black
}

private fun shouldShowAmount(card: Card?, isAbomination: Boolean): Boolean {
    // Trejo and Extra Activation cards use custom bottom rules instead of count display.
    val isTrejo = card?.zombieType == ZombieType.TREJO
    return !isAbomination && !isTrejo && card?.cardType != CardType.EXTRA_ACTIVATION
}

@Composable
private fun BoxScope.ZombieCardBack() {
    // Back face shown when no card content is available.
    Image(
        painter = painterResource(R.drawable.card_back),
        contentDescription = "Card back",
        contentScale = ContentScale.Crop,
        modifier = Modifier.matchParentSize()
    )
}

@Composable
private fun BoxScope.ZombieCardFrontBackground() {
    // Shared textured front background for all revealed cards.
    Image(
        painter = painterResource(R.drawable.bg_card),
        contentDescription = "Card background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.matchParentSize()
    )
}

@Composable
private fun ZombieCardTop(
    card: Card?,
    abomination: Abomination?,
    isAbomination: Boolean,
    fontColor: Color,
    stripeColor: Color,
    topBgColor: Color,
) {
    // Title source depends on whether this is a regular zombie card or an abomination card.
    val title = when {
        abomination != null -> stringResource(id = abomination.nameRes).uppercase()
        card != null -> stringResource(id = card.zombieType.nameRes).uppercase()
        else -> return
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
    ) {
        // Decorative top strip is rotated/scaled to match the physical card style.
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
                text = title,
                color = fontColor,
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
            )
            if (!isAbomination && card != null) {
                // Card number is only meaningful for standard zombie cards.
                val cardId = stringResource(
                    R.string.card_number,
                    String.format(Locale.ROOT, "%03d", card.id)
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
}

@Composable
private fun BoxScope.ZombieCardImage(card: Card?, abomination: Abomination?, isAbomination: Boolean) {
    // Artwork source changes with entity type, but placement logic remains shared.
    val imageRes = when {
        abomination != null -> abomination.imageRes
        card != null -> card.zombieType.imageRes
        else -> return
    }

    Image(
        painter = painterResource(imageRes),
        contentDescription = stringResource(id = R.string.not_important),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .matchParentSize()
            .offset(if (isAbomination) 0.dp else (-30).dp, 20.dp)
    )
}

@Composable
private fun ZombieAmount(card: Card?, danger: Danger, modifier: Modifier = Modifier) {
    if (card == null) return

    AnimatedContent(
        targetState = danger,
        label = "Danger level change",
        transitionSpec = {
            // Slide direction follows danger progression for better visual feedback.
            if (targetState > initialState) {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() togetherWith
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                // Keep the same transition while allowing content to slide outside bounds.
                SizeTransform(clip = false)
            )
        },
        modifier = modifier
    ) { targetDanger ->
        val dangerColor = colorResource(targetDanger.colorRes)
        Text(
            text = stringResource(id = R.string.amount, card.getAmount(targetDanger)),
            color = colorResource(targetDanger.getTextColor()),
            fontFamily = sonoMonoFont,
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

@Composable
private fun BoxScope.ShooterBadge() {
    // Small marker used by special shooter cards.
    Image(
        painter = painterResource(R.drawable.shooter_badge),
        contentDescription = stringResource(id = R.string.not_important),
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .offset(x = (-10).dp, y = (-45).dp)
    )
}

@Composable
private fun BoxScope.ZombieCardBottom(
    card: Card?,
    abomination: Abomination?,
    danger: Danger,
    fontColor: Color,
) {
    // Abominations always display their dedicated rule box at the bottom.
    if (abomination != null) {
        Text(
            text = stringResource(abomination.ruleRes),
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
        return
    }

    if (card == null) return

    // Trejo has fully custom text and background depending on SPAWN vs RUSH.
    if (card.zombieType == ZombieType.TREJO) {
        val text = stringResource(
            id = if (card.cardType == CardType.SPAWN) {
                R.string.trejo_rule
            } else {
                R.string.trejo_rush_rule
            }
        )
        val backgroundColor = if (card.cardType == CardType.SPAWN) {
            R.color.black
        } else {
            R.color.danger_yellow
        }
        Text(
            text = AnnotatedString.fromHtml(text),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(colorResource(id = backgroundColor))
                .padding(vertical = 16.dp, horizontal = 8.dp),
            color = fontColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
        return
    }

    // SPAWN cards have no additional bottom text in this section.
    if (card.cardType == CardType.SPAWN) return

    // Non-SPAWN standard cards map to a short action reminder.
    val text = when (card.cardType) {
        CardType.EXTRA_ACTIVATION -> {
            if (card.getAmount(danger) > 0) stringResource(id = R.string.one_extra_activation)
            else stringResource(id = R.string.no_extra_activation)
        }

        CardType.RUSH -> stringResource(id = R.string.spawn_then_activate)
    }
    Text(
        text = text,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp),
        color = colorResource(id = R.color.white),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun DrawZombieCardFattyRushPreview() {
    ZombieCardPreview(
        card = Card(
            7,
            CardType.RUSH,
            ZombieType.FATTY,
            listOf(0, 4, 6, 8)
        ),
        danger = Danger.BLUE
    )
}

@Preview
@Composable
fun DrawZombieCardRunnerSpawnPreview() {
    ZombieCardPreview(
        card = Card(
            7,
            CardType.SPAWN,
            ZombieType.RUNNER,
            listOf(2, 4, 6, 8)
        ),
        danger = Danger.ORANGE
    )
}

@Preview
@Composable
fun DrawZombieCardWalkerExtraPreview() {
    ZombieCardPreview(
        card = Card(
            7,
            CardType.EXTRA_ACTIVATION,
            ZombieType.WALKER,
            listOf(2, 4, 6, 8)
        ),
        danger = Danger.YELLOW
    )
}

@Preview
@Composable
fun DrawZombieCardShooter() {
    ZombieCardPreview(
        card = Card(
            72,
            CardType.SPAWN,
            ZombieType.FATTY,
            listOf(1, 2, 3, 4),
            true
        ),
        danger = Danger.BLUE
    )
}


@Preview
@Composable
fun DrawZombieCardAbominawildPreview() {
    ZombieCardPreview(abomination = Abomination.ABOMINAWILD)
}

@Preview
@Composable
fun DrawZombieCardPatient0Preview() {
    ZombieCardPreview(abomination = Abomination.PATIENT_0)
}

@Preview
@Composable
fun DrawZombieCardAbominacopPreview() {
    ZombieCardPreview(abomination = Abomination.ABOMINACOP)
}

@Preview
@Composable
fun DrawZombieCardHobominationPreview() {
    ZombieCardPreview(abomination = Abomination.HOBOMINATION)
}

@Preview(name = "Trejo")
@Preview(name = "Trejo fr", locale = "fr")
@Composable
fun DrawZombieCardTrejoPreview() {
    ZombieCardPreview(
        card = Card(
            81,
            CardType.SPAWN,
            ZombieType.TREJO,
            listOf()
        )
    )
}


@Preview(name = "Trejo rush")
@Preview(name = "Trejo rush fr", locale = "fr")
@Composable
fun DrawZombieCardTrejoRushPreview() {
    ZombieCardPreview(
        card = Card(
            86,
            CardType.RUSH,
            ZombieType.TREJO,
            listOf()
        )
    )
}

@Preview
@Composable
fun DrawZombieCardNonePreview() {
    ZombieCardPreview()
}

@Composable
private fun ZombieCardPreview(
    card: Card? = null,
    abomination: Abomination? = null,
    danger: Danger = Danger.BLUE,
) {
    ZombieDeckTheme {
        ZombieCard(card = card, abomination = abomination, danger = danger)
    }
}

