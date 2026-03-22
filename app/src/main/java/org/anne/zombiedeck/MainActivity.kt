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
    private var abominationSoundId: Int = 0
    private var shooterSoundId: Int = 0

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

        abominationSoundId = soundPool?.load(this, R.raw.growling_zombie, 1) ?: 0
        shooterSoundId = soundPool?.load(this, R.raw.gunshot, 1) ?: 0

        setContent {
            ZombieDeckTheme {
                ZombieDeckApp(
                    playAbominationSound = {
                        soundPool?.play(abominationSoundId, 1f, 1f, 1, 0, 1f)
                    },
                    playShooterSound = {
                        soundPool?.play(shooterSoundId, 1f, 1f, 1, 0, 1f)
                    },
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
