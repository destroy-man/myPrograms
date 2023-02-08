package ru.korobeynikov.mydictionary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.text.StringBuilder

class MainPresenter(private var mainModel: MainModel, private val scope: CoroutineScope) {

    private var view: MainActivity? = null //объект View
    private lateinit var listWords: List<Word> //список слов

    //присоединить объект View
    fun attachView(mainActivity: MainActivity) {
        view = mainActivity
    }

    //отсоединить объект View
    fun detachView() {
        view = null
    }

    fun getListWords() {
        scope.launch {
            listWords = mainModel.getWordsFromRealm()
            withContext(Dispatchers.Main) {
                view?.showListWords(getFilterListWords())
            }
        }
    }

    //Фильтрация списка слов без параметров
    private fun getFilterListWords(): StringBuilder {
        val allWords = StringBuilder()
        if (listWords.isNotEmpty()) {
            for (word in listWords)
                if (word.transcription.isNotEmpty())
                    allWords.append(" ${word.original} (${word.transcription}) = ${word.translation}\n")
                else
                    allWords.append(" ${word.original} = ${word.translation}\n")
        }
        return allWords
    }

    //Фильтрация списка слов с параметрами
    fun getFilterListWords(findWord: String, fieldName: String, cutList: Boolean,
                           showTranslation: Boolean, countWordShowString: String): StringBuilder {
        val filterListWords = if (!cutList) {
            if (fieldName == "original")
                listWords.filter { word -> word.original.contains(findWord, true) }
            else
                listWords.filter { word -> word.translation.contains(findWord, true) }
        } else
            listWords
        var indexCut = 0
        val countWordsShow =
            when {
                countWordShowString.isEmpty() -> -1
                else -> countWordShowString.toInt()
            }
        val allWords = StringBuilder()
        if (filterListWords.isNotEmpty()) {
            if (cutList) {
                indexCut =
                    if (fieldName == "original")
                        filterListWords.indexOf(filterListWords.find { word ->
                            word.original.startsWith(findWord)
                        })
                    else
                        filterListWords.indexOf(filterListWords.find { word ->
                            word.translation.startsWith(findWord)
                        })
                if (indexCut < 0) indexCut = 0
            }
            var lastIndex = indexCut + countWordsShow
            if (countWordsShow == -1 || lastIndex > filterListWords.size)
                lastIndex = filterListWords.size
            for (index in indexCut until lastIndex) {
                val word = filterListWords[index]
                if (showTranslation) {
                    if (fieldName == "original") {
                        if (word.transcription.isNotEmpty())
                            allWords.append(" ${word.original} (${word.transcription}) = " +
                                    "${word.translation}\n")
                        else
                            allWords.append(" ${word.original} = ${word.translation}\n")
                    } else {
                        if (word.transcription.isNotEmpty())
                            allWords.append(" ${word.translation} = ${word.original} " +
                                    "(${word.transcription})\n")
                        else
                            allWords.append(" ${word.translation} = ${word.original}\n")
                    }
                } else if (fieldName == "translation")
                    allWords.append(" ${word.translation}\n")
                else {
                    if (word.transcription.isNotEmpty())
                        allWords.append(" ${word.original} (${word.transcription})\n")
                    else
                        allWords.append(" ${word.original}\n")
                }
            }
        }
        return allWords
    }

    fun addWord(originalText: String, transcriptionText: String, translationText: String) {
        val word = listWords.find { word -> word.original == originalText }
        if (word == null && translationText.isEmpty())
            view?.showMessage("Для добавления слова в словарь нужно заполнить поле Перевод!")
        else
            scope.launch {
                mainModel.addWordInRealm(originalText, transcriptionText, translationText)
                getListWords()
            }
    }

    fun deleteWord(originalText: String) {
        scope.launch {
            mainModel.deleteWordFromRealm(originalText)
            getListWords()
        }
    }

    fun saveWords(path: String) {
        scope.launch {
            val message = mainModel.saveWordsInFile(path, listWords)
            withContext(Dispatchers.Main) {
                view?.showMessage(message)
            }
        }
    }

    fun loadWords(path: String) {
        scope.launch {
            val message = mainModel.loadWordsFromFile(path)
            getListWords()
            withContext(Dispatchers.Main) {
                view?.showMessage(message)
            }
        }
    }
}