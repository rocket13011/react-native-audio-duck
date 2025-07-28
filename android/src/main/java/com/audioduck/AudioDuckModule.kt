package com.audioduck

import android.content.Context
import android.media.*
import com.facebook.react.bridge.*
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = AudioDuckModule.NAME)
class AudioDuckModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  companion object {
    const val NAME = "AudioDuck"
  }

  private var mediaPlayer: MediaPlayer? = null

  override fun getName(): String = NAME

  @ReactMethod
  fun play(options: ReadableMap, promise: Promise) {
    val fileName = options.getString("fileName") ?: run {
      promise.reject("MISSING_FILE", "Missing fileName")
      return
    }

    val duckOtherAudio = options.getBoolean("duckOtherAudio")

    if (duckOtherAudio) {
      requestAudioFocus()
    }

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
