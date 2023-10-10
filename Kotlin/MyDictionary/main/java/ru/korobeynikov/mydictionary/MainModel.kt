package ru.korobeynikov.mydictionary

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import org.bson.types.ObjectId
import java.io.*

class MainModel(private var config: RealmConfiguration) {

    private lateinit var realm: Realm //объект необходимый для взаимодействия с базой данных

    suspend fun getWordsFromRealm(): List<Word> {
        val listWords = ArrayList<Word>()
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            val listWordsFromRealm =
                realmTransaction.where(WordRealm::class.java).sort("original").findAll()
            if (listWordsFromRealm.isNotEmpty())
                for (word in listWordsFromRealm)
                    listWords.add(Word(word.original, word.transcription, word.translation))
        }
        realm.close()
        return listWords
    }

    suspend fun addWordInRealm(originalText: String, transcriptionText: String, translationText: String) {
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            addWord(realmTransaction, originalText, transcriptionText, translationText)
        }
        realm.close()
    }

    suspend fun deleteWordFromRealm(originalText: String) {
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            val word = realmTransaction.where(WordRealm::class.java)
                .equalTo("original", originalText).sort("original").findFirst()
            word?.deleteFromRealm()
        }
        realm.close()
    }

    fun saveWordsInFile(path: String, listWords: List<Word>): String {
        val directory = File(path, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        return try {
            if (listWords.isNotEmpty()) {
                val writer = BufferedWriter(FileWriter(fileDictionary))
                for (word in listWords) {
                    if (word.transcription.isNotEmpty())
                        writer.write("${word.original} (${word.transcription})=${word.translation}")
                    else
                        writer.write("${word.original}=${word.translation}")
                    writer.newLine()
                }
                writer.close()
            }
            "Сохранение в файл успешно завершено!"
        } catch (ex: IOException) {
            "Произошла ошибка при сохранении в файл!"
        }
    }

    suspend fun loadWordsFromFile(path: String): String {
        val directory = File(path, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        val allWords = StringBuilder()
        return try {
            val reader = BufferedReader(FileReader(fileDictionary))
            var str = reader.readLine()
            while (str != null) {
                allWords.append("$str\n")
                str = reader.readLine()
            }
            reader.close()
            if (allWords.isNotEmpty()) {
                realm = Realm.getInstance(config)
                realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                    for (strWord in allWords.split("\n")) {
                        if (strWord.isEmpty()) continue
                        var originalWord = strWord.split("=")[0]
                        var transcriptionWord = ""
                        if (originalWord.indexOf('(') != -1) {
                            val begin = originalWord.indexOf('(')
                            val end = originalWord.lastIndex
                            transcriptionWord = originalWord.substring(begin + 1, end)
                            originalWord = originalWord.substring(0, begin - 1)
                        }
                        val translationWord = strWord.split("=")[1]
                        addWord(realmTransaction, originalWord, transcriptionWord, translationWord)
                    }
                }
                realm.close()
            }
            "Данные из файла успешно загружены!"
        } catch (ex: IOException) {
            "Произошла ошибка при загрузке данных из файла!"
        }
    }

    private fun addWord(realmTransaction: Realm, originalText: String, transcriptionText: String,
                        translationText: String) {
        val word = realmTransaction.where(WordRealm::class.java)
            .equalTo("original", originalText).findFirst()
        if (word != null) {
            if (transcriptionText.isNotEmpty())
                word.transcription = transcriptionText
            if (translationText.isNotEmpty())
                word.translation = translationText
            realmTransaction.copyToRealmOrUpdate(word)
        } else
            realmTransaction.copyToRealmOrUpdate(
                WordRealm(
                    ObjectId().toHexString(),
                    originalText,
                    transcriptionText,
                    translationText
                )
            )
    }
}