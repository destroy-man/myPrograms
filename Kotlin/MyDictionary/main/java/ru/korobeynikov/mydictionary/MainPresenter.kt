package ru.korobeynikov.mydictionary

import android.content.Context

class MainPresenter(private var mainModel: MainModel) {

    lateinit var listWords: List<WordForView>
    lateinit var message: String

    suspend fun saveWordsInFile(context: Context) {
        message = mainModel.writeFile(context)
    }

    suspend fun loadWordsFromFile(context: Context) {
        message = mainModel.readFile(context)
    }

    suspend fun addWord(originalText: String, translationText: String) {
        message = mainModel.addWordInRealm(originalText, translationText)
    }

    suspend fun deleteWord(originalText: String) {
        message = mainModel.deleteWordFromRealm(originalText)
    }

    suspend fun getListWords(findWord: String, fieldName: String, cutList: Boolean) {
        listWords = mainModel.getWordsFromRealm(findWord, fieldName, cutList)
    }
}