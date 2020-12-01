package fr.enssat.babelblock.sueur_wieczorek.screens.translator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.mlkit.nl.translate.TranslateLanguage
import fr.enssat.babelblock.sueur_wieczorek.BabelBlockService
import fr.enssat.babelblock.sueur_wieczorek.TextToSpeechTool
import fr.enssat.babelblock.sueur_wieczorek.TranslationTool
import fr.enssat.babelblock.sueur_wieczorek.tools.Language
import java.util.*

class TranslatorViewModel(application: Application) : AndroidViewModel(application) {

    var service: BabelBlockService = BabelBlockService(getApplication())
    var translator: TranslationTool
    var textToSpeech: TextToSpeechTool
    val availableLanguages: List<Language> = TranslateLanguage.getAllLanguages()
        .map { Language(it) }

    init {
        translator = service.translator(Locale.FRENCH, Locale.ENGLISH)
        textToSpeech = service.textToSpeech()
    }

    override fun onCleared() {
        super.onCleared()

        translator.close()
        textToSpeech.close()
    }
}