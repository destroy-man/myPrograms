package ru.korobeynikov.mydictionary

import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import org.bson.types.ObjectId
import java.io.*

class MainModel(private var config: RealmConfiguration) {

    private lateinit var realm: Realm //объект необходимый для взаимодействия с базой данных
    private val logTag = "myErrors" //метка для логгирования ошибок

    suspend fun getWordsFromRealm(): List<Word> {
        val listWords = ArrayList<Word>()
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            val listWordsFromRealm = realmTransaction.where(WordRealm::class.java)
                .sort("original").findAll()
            if (listWordsFromRealm.isNotEmpty())
                for (word in listWordsFromRealm)
                    listWords.add(Word(word.original, word.translation))
        }
        realm.close()
        return listWords
    }

    suspend fun addWordInRealm(originalText: String, translationText: String) {
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            addWord(realmTransaction, originalText, translationText)
        }
        realm.close()
    }

    suspend fun deleteWordFromRealm(originalText: String) {
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            val word = realmTransaction.where(WordRealm::class.java).equalTo("original",
                originalText).sort("original").findFirst()
            word?.deleteFromRealm()
        }
        realm.close()
    }

    fun saveWordsInFile(path: String, listWords: List<Word>) {
        val directory = File(path, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        try {
            if (listWords.isNotEmpty()) {
                val writer = BufferedWriter(FileWriter(fileDictionary))
                for (word in listWords) {
                    writer.write("${word.original}=${word.translation}")
                    writer.newLine()
                }
                writer.close()
            }
        } catch (ex: IOException) {
            Log.d(logTag, ex.message.toString())
        }
    }

    suspend fun loadWordsFromFile(path: String) {
        val directory = File(path, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        val allWords = StringBuilder()
        try {
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
                        val originalWord = strWord.split("=")[0]
                        val translationWord = strWord.split("=")[1]
                        addWord(realmTransaction, originalWord, translationWord)
                    }
                }
                realm.close()
            }
        } catch (ex: IOException) {
            Log.d(logTag, ex.message.toString())
        }
    }

    private fun addWord(realmTransaction: Realm, originalText: String, translationText: String) {
        val word = realmTransaction.where(WordRealm::class.java)
            .equalTo("original", originalText).findFirst()
        if (word != null) {
            word.translation = translationText
            realmTransaction.copyToRealmOrUpdate(word)
        } else
            realmTransaction.copyToRealmOrUpdate(WordRealm(ObjectId().toHexString(), originalText,
                translationText))
    }
}