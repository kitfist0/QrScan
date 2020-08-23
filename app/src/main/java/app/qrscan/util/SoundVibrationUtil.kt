package app.qrscan.util

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class SoundVibrationUtil(private val context: Context) {

    companion object {
        private const val VIBRATION_DURATION_MS = 150L
        private const val SOUND_FILE_NAME = "beep.mp3"
    }

    fun playSound() {
        var mMediaPlayer = MediaPlayer()
        try {
            if (mMediaPlayer.isPlaying) {
                mMediaPlayer.stop()
                mMediaPlayer.release()
                mMediaPlayer = MediaPlayer()
            }
            val descriptor: AssetFileDescriptor = context.assets.openFd(SOUND_FILE_NAME)
            mMediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()
            mMediaPlayer.prepare()
            mMediaPlayer.setVolume(1f, 1f)
            mMediaPlayer.isLooping = false
            mMediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun vibrate() {
        val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION_MS,
                        VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(VIBRATION_DURATION_MS)
            }
        }
    }
}
