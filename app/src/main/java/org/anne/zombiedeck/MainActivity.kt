package org.anne.zombiedeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.anne.zombiedeck.ui.theme.ZombieDeckTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ZombieDeckTheme {
                ZombieDeckApp()
            }
        }
    }
}
