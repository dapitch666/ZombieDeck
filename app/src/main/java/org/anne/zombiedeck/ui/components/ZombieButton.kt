package org.anne.zombiedeck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.anne.zombiedeck.R

@Composable
fun ZombieButton(
    buttonText: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(R.color.danger_yellow),
    foregroundColor: Color = Color.Black,
    onClick: (isEnabled: Boolean) -> Unit = {},
    enable: Boolean = true,
    fillWidth: Boolean = false,
) {
    Button(
        onClick = { onClick(enable) },
        modifier = modifier.conditional(!enable, { disabled() }),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp,
            hoveredElevation = 10.dp,
            focusedElevation = 10.dp
        ),
        enabled = enable,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    createStripeBrush(
                        foregroundColor,
                        backgroundColor,
                        10.dp
                    )
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor)
                    .align(Alignment.Center)
                    .padding(10.dp, 5.dp, 10.dp, 5.dp)
                    .conditional(
                        fillWidth,
                        { fillMaxWidth() },
                        { wrapContentSize() }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = buttonText,
                    color = foregroundColor,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }


        }
    }
}

@Preview
@Composable
fun ZombieButtonPreview() {
    ZombieButton(
        buttonText = "Button"
    )
}

@Preview
@Composable
fun ZombieButtonDisabledPreview() {
    ZombieButton(
        buttonText = "Button",
        enable = false,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun ZombieButtonsOnARowPreview() {
    Row(
//        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ZombieButton(
            buttonText = "Button 1",
            enable = false,
            modifier = Modifier.weight(1f),
            fillWidth = true
        )
        ZombieButton(
            buttonText = "Long button text",
            enable = true,
            modifier = Modifier.weight(1f),
            fillWidth = true
        )
    }
}