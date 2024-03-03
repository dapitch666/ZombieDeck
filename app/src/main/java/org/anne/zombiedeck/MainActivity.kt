package org.anne.zombiedeck

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.anne.zombiedeck.ui.theme.ZombieDeckTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZombieDeckTheme {
                ZombieDeckApp(
                    playAbominationSound = { playAbominationSound() }
                )
            }
        }
    }

    private fun playAbominationSound() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val soundPool: SoundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        val sound = soundPool.load(this, R.raw.growling_zombie, 1)
        soundPool.play(sound, 1f, 1f, 1, 0, 1f)
    }
}
