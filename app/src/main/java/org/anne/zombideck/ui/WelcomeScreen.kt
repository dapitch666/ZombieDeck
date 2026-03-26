package org.anne.zombideck.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.anne.zombideck.BuildConfig
import org.anne.zombideck.R
import org.anne.zombideck.ui.components.ZombieButton
import org.anne.zombideck.ui.theme.ZombiDeckTheme

@Composable
fun WelcomeScreen(
    navigateToDraw: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Full-bleed poster establishes the app atmosphere on first launch.
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
            // Top logo keeps branding visible regardless of screen ratio.
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
                // Primary actions are grouped in a single block for clear onboarding.
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

        // Subtle build/version label for support and bug reports.
        Text(
            text = stringResource(R.string.app_version_label, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        )
    }
}

@Preview
@Preview(locale = "fr")
@Composable
fun WelcomeScreenPreview() {
    ZombiDeckTheme {
        WelcomeScreen(
            navigateToDraw = { },
            navigateToSettings = { }
        )
    }
}