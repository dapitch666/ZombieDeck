package org.anne.zombiedeck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombiedeck.R
import org.anne.zombiedeck.ui.components.ZombieButton
import org.anne.zombiedeck.ui.theme.ZombieDeckTheme

@Composable
fun WelcomeScreen(
    navigateToDraw: (Boolean) -> Unit,
    navigateToSettings: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.poster),
            contentDescription = stringResource(id = R.string.not_important),
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.not_important),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ZombieButton(
                    onClick = navigateToDraw,
                    buttonText = stringResource(id = R.string.shuffle_and_start)
                )
                ZombieButton(
                    onClick = navigateToSettings,
                    buttonText = stringResource(R.string.configure_the_deck)
                )
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    ZombieDeckTheme {
        WelcomeScreen(
            navigateToDraw = { },
            navigateToSettings = { }
        )
    }
}