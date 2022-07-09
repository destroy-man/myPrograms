package ru.korobeynikov.mydictionary

class MainPresenter(private var mainModel: MainModel) {

    lateinit var listWords: List<WordForView>
    lateinit var message: String

    suspend fun saveWordsInFile(path: String) {
        message = mainModel.writeFile(path)
    }

    suspend fun loadWordsFromFile(path: String) {
        message = mainModel.readFile(path)
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