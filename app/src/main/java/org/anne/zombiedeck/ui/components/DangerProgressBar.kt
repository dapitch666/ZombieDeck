package org.anne.zombiedeck.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.anne.zombiedeck.R
import org.anne.zombiedeck.data.Danger

@Composable
fun DangerProgressBar(
    currentProgress: Float,
    danger: Danger,
    modifier: Modifier = Modifier,
) {
    val progress by animateFloatAsState(
        targetValue = currentProgress,
        label = "Progress bar animation"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = danger.colorRes))
            .height(36.dp)
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .background(
                    createStripeBrush(
                        colorResource(id = R.color.black),
                        colorResource(id = danger.colorRes),
                        10.dp
                    )
                )
                .fillMaxHeight()
                .fillMaxWidth(progress)
        )
        Text(
            text = stringResource(R.string.danger_level, stringResource(id = danger.nameRes)),
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun DangerProgressBarPreview() {
    DangerProgressBar(
        currentProgress = 0.5f,
        danger = Danger.ORANGE
    )
}
