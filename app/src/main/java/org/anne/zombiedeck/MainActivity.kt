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

    private var soundPool: SoundPool? = null
    private var soundId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        soundId = soundPool?.load(this, R.raw.growling_zombie, 1) ?: 0

        setContent {
            ZombieDeckTheme {
                ZombieDeckApp(
                    playAbominationSound = {
                        soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool?.release()
        soundPool = null
    }
}
