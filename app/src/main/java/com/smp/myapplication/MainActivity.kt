package com.smp.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.media.session.PlaybackState.STATE_PLAYING
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var mediaSession: MediaSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.ic_launcher3
        )

        val playbackState = PlaybackState.Builder()
            .setState(STATE_PLAYING, 0L, 1.0f)
            .build()

        val md: MediaMetadata = MediaMetadata.Builder()
            .putString(MediaMetadata.METADATA_KEY_ARTIST, "eep")
            .putString(MediaMetadata.METADATA_KEY_ALBUM, "foo")
            .putString(MediaMetadata.METADATA_KEY_TITLE, "bar")
            //.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, processor.getDuration() / 1000)
            .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, bitmap)
            .putBitmap(MediaMetadata.METADATA_KEY_ART, bitmap)
            .build()



        mediaSession = MediaSession(this.applicationContext, "TESTER"). apply {
            isActive = true
            setMetadata(md)
            setPlaybackState(playbackState)
        }
        ContextCompat.startForegroundService(this, Intent(this, MyService::class.java))
    }
}
