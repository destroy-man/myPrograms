package ru.korobeynikov.mydictionary

import kotlin.text.StringBuilder

class MainPresenter(private var mainModel: MainModel) {

    private lateinit var listWords: List<String> //список слов полученный из базы данных

    suspend fun getListWords() {
        listWords = mainModel.getWordsFromRealm()
    }

    //Фильтрация списка слов
    fun getFilterListWords(findWord: String, fieldName: String, cutList: Boolean,
        showTranslation: Boolean, countWordShowString: String): StringBuilder {
        val filterListWords = if (!cutList) {
            if (fieldName == "original")
                listWords.filter { it.split("=")[0].contains(findWord, true) }
            else
                listWords.filter { it.split("=")[1].contains(findWord, true) }
        } else
            listWords
        val listOriginal = filterListWords.map { word -> word.split("=")[0] }
        val listTranslation = filterListWords.map { word -> word.split("=")[1] }
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
                        listOriginal.indexOf(listOriginal.find { original -> original.startsWith(findWord) })
                    else
                        listTranslation.indexOf(listTranslation.find { translation -> translation.startsWith(findWord) })
                if (indexCut < 0) indexCut = 0
            }
            var lastIndex = indexCut + countWordsShow
            if (countWordsShow == -1 || lastIndex > filterListWords.size)
                lastIndex = filterListWords.size
            for (index in indexCut until lastIndex)
                if (showTranslation) {
                    if (fieldName == "original")
                        allWords.append(" ${listOriginal[index]} = ${listTranslation[index]}\n")
                    else
                        allWords.append(" ${listTranslation[index]} = ${listOriginal[index]}\n")
                } else if (fieldName == "translation")
                    allWords.append(" ${listTranslation[index]}\n")
                else
                    allWords.append(" ${listOriginal[index]}\n")
        }
        return allWords
    }

    suspend fun addWord(originalText: String, translationText: String) {
        mainModel.addWordInRealm(originalText, translationText)
        getListWords()
    }

    suspend fun deleteWord(originalText: String) {
        mainModel.deleteWordFromRealm(originalText)
        getListWords()
    }

    fun saveWords(path: String) {
        mainModel.saveWordsInFile(path, listWords)
    }

    suspend fun loadWords(path: String) {
        mainModel.loadWordsFromFile(path)
        getListWords()
    }
}