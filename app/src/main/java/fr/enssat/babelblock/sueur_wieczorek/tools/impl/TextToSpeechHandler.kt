package fr.enssat.babelblock.tools.impl
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import fr.enssat.babelblock.sueur_wieczorek.TextToSpeechTool
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import java.util.*

class TextToSpeechHandler(context: Context, val locale: Locale): TextToSpeechTool {

    private val speaker = TextToSpeech(context, object: TextToSpeech.OnInitListener {
        override fun onInit(status: Int) {
            Log.d("Speak", "status: $status")
        }
    })

    override fun speak(text: String, loc: Locale) {
        speaker.language = loc
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }



    override fun stop() {
        speaker.stop()
    }

    override fun close() {
        speaker.shutdown()
    }
}