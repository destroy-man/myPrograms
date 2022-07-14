package ru.korobeynikov.mydictionary

import kotlin.text.StringBuilder

class MainPresenter(private var mainModel: MainModel) {

    lateinit var message: String

    suspend fun saveWordsInFile(path: String): StringBuilder {
        message = mainModel.writeFile(path)
        return getListWords()
    }

    suspend fun loadWordsFromFile(path: String): StringBuilder {
        message = mainModel.readFile(path)
        return getListWords()
    }

    suspend fun addWord(originalText: String, translationText: String): StringBuilder {
        message = mainModel.addWordInRealm(originalText, translationText)
        return getListWords()
    }

    suspend fun deleteWord(originalText: String): StringBuilder {
        message = mainModel.deleteWordFromRealm(originalText)
        return getListWords()
    }

    private suspend fun getListWords(): StringBuilder {
        val listWords = mainModel.getWordsFromRealm("", "original", false)
        val allWords = StringBuilder()
        if (listWords.isNotEmpty())
            for (word in listWords)
                allWords.append(" ${word.original} = ${word.translation}\n")
        return allWords
    }

    suspend fun getListWords(findWord: String, fieldName: String, cutList: Boolean, showTranslation: Boolean,
                             countWordShowString: String): StringBuilder {
        message = ""
        val listWords = mainModel.getWordsFromRealm(findWord, fieldName, cutList)
        var indexCut = 0
        val countWordsShow =
            when {
                countWordShowString.isEmpty() -> -1
                else -> countWordShowString.toInt()
            }
        val allWords = StringBuilder()
        if (listWords.isNotEmpty()) {
            if (cutList)
                indexCut =
                    if (fieldName == "original")
                        listWords.indexOf(listWords.find { word -> word.original.startsWith(findWord) })
                    else
                        listWords.indexOf(listWords.find { word -> word.translation.startsWith(findWord) })
            var lastIndex = indexCut + countWordsShow
            if (countWordsShow == -1 || lastIndex > listWords.size)
                lastIndex = listWords.size
            for (index in indexCut until lastIndex) {
                val word = listWords[index]
                if (showTranslation) {
                    if (fieldName == "original")
                        allWords.append(" ${word.original} = ${word.translation}\n")
                    else
                        allWords.append(" ${word.translation} = ${word.original}\n")
                } else if (fieldName == "translation")
                    allWords.append(" ${word.translation}\n")
                else
                    allWords.append(" ${word.original}\n")
            }
        }
        return allWords
    }
}