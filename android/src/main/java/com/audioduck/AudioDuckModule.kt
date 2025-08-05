package com.audioduck

import android.content.Context
import android.media.*
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule

import android.util.Log


@ReactModule(name = AudioDuckModule.NAME)
class AudioDuckModule(reactContext: ReactApplicationContext) :
  NativeAudioDuckSpec(reactContext) {

  companion object {
    const val NAME = "AudioDuck"
  }

  private var mediaPlayer: MediaPlayer? = null

  override fun getName(): String = NAME

  @ReactMethod
  override fun play(options: ReadableMap, promise: Promise) {
    Log.d("AudioDuck", "play called with options: $options");
    val fileName = if (options.hasKey("fileName")) options.getString("fileName") else null
    val uri = if (options.hasKey("uri")) options.getString("uri") else null
    val duckOtherAudio = if (options.hasKey("duckOtherAudio")) options.getBoolean("duckOtherAudio") else false

    if (duckOtherAudio) {
      requestAudioFocus()
    }

    when {
      uri != null -> {
        try {
          mediaPlayer = MediaPlayer().apply {
            setDataSource(uri)
            setOnCompletionListener {
              abandonAudioFocus()
              promise.resolve(null)
            }
            prepare()
            start()
          }
        } catch (e: Exception) {
          promise.reject("PLAYER_ERROR", e.message, e)
          abandonAudioFocus()
        }
      }
      fileName != null -> {
        val resId = reactApplicationContext.resources.getIdentifier(
          fileName, "raw", reactApplicationContext.packageName
        )
        if (resId == 0) {
          promise.reject("FILE_NOT_FOUND", "Audio file not found in raw folder")
          return
        }
        mediaPlayer = MediaPlayer.create(reactApplicationContext, resId).apply {
          setOnCompletionListener {
            abandonAudioFocus()
            promise.resolve(null)
          }
          start()
        }
      }
      else -> {
        promise.reject("MISSING_FILE", "You must provide either `uri` or `fileName`")
      }
    }
  }

  private var audioFocusRequest: AudioFocusRequest? = null
  private val audioManager by lazy {
    reactApplicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
  }

  private fun requestAudioFocus() {
    val audioAttributes = AudioAttributes.Builder()
      .setUsage(AudioAttributes.USAGE_MEDIA)
      .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
      .build()

    audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
      .setAudioAttributes(audioAttributes)
      .setAcceptsDelayedFocusGain(false)
      .setOnAudioFocusChangeListener { }
      .build()

    audioManager.requestAudioFocus(audioFocusRequest!!)
  }

  private fun abandonAudioFocus() {
    audioFocusRequest?.let {
      audioManager.abandonAudioFocusRequest(it)
    }
    audioFocusRequest = null
  }

  override fun onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy()
    mediaPlayer?.release()
    abandonAudioFocus()
  }
}

