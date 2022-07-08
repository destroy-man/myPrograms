package ru.korobeynikov.mydictionary

import android.os.Environment
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import org.bson.types.ObjectId
import java.io.*

class MainModel(private var config: RealmConfiguration) {

    lateinit var realm: Realm
    var isRealmClosed = true

    suspend fun writeFile(): String {
        val directory = File(Environment.getExternalStorageDirectory().absolutePath, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        val allWords = StringBuilder()
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            val listWords: List<Word> = realmTransaction.where(Word::class.java).sort("original").findAll()
            if (listWords.isNotEmpty())
                for (word in listWords)
                    allWords.append("${word.original}=${word.translation}\n")
        }
        realm.close()
        return if (allWords.isNotEmpty())
            try {
                val writer = BufferedWriter(FileWriter(fileDictionary))
                writer.write(allWords.toString())
                writer.close()
                "Сохранение в файл успешно завершено!"
            } catch (e: IOException) {
                "Произошла ошибка при сохранении в файл!"
            }
        else
            "Нет данных для сохранения!"
    }

    suspend fun readFile(): String {
        val directory = File(Environment.getExternalStorageDirectory().absolutePath, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        val allWords = StringBuilder()
        try {
            val reader = BufferedReader(FileReader(fileDictionary))
            var str = reader.readLine()
            while (str != null) {
                allWords.append(str + "\n")
                str = reader.readLine()
            }
            reader.close()
        } catch (e: IOException) {
            return "Произошла ошибка при загрузке данных из файла!"
        }
        return if (allWords.isNotEmpty()) {
            realm = Realm.getInstance(config)
            realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                for (strWord in allWords.split("\n")) {
                    if (strWord.isEmpty()) continue
                    val originalWord = strWord.split("=")[0]
                    val translationWord = strWord.split("=")[1]
                    val word = realmTransaction.where(Word::class.java)
                        .equalTo("original", originalWord).findFirst()
                    if (word != null) {
                        word.translation = translationWord
                        realmTransaction.copyToRealmOrUpdate(word)
                    } else
                        realmTransaction.copyToRealmOrUpdate(Word(ObjectId().toHexString()
                            , originalWord, translationWord))
                }
            }
            realm.close()
            "Данные из файла успешно загружены!"
        } else
            "В файле не обнаружено данных для загрузки!"
    }

    suspend fun addWordInRealm(originalText: String, translationText: String): String {
        return if (originalText.isNotEmpty() && translationText.isNotEmpty()) {
            var message = ""
            realm = Realm.getInstance(config)
            realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                val word = realmTransaction.where(Word::class.java)
                    .equalTo("original", originalText).findFirst()
                if (word != null) {
                    word.translation = translationText
                    realmTransaction.copyToRealmOrUpdate(word)
                    message = "Слово изменено в словаре!"
                } else {
                    realmTransaction.copyToRealmOrUpdate(Word(ObjectId().toHexString()
                        , originalText, translationText))
                    message = "Слово добавлено в словарь!"
                }
            }
            realm.close()
            message
        } else if (originalText.isEmpty())
            "Для добавления слова в словарь нужно заполнить поле Слово!"
        else
            "Для добавления слова в словарь нужно заполнить поле Перевод!"
    }

    suspend fun deleteWordFromRealm(originalText: String): String {
        return if (originalText.isNotEmpty()) {
            var isWordExisted = true
            realm = Realm.getInstance(config)
            realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                val word = realmTransaction.where(Word::class.java)
                    .equalTo("original", originalText).sort("original").findFirst()
                if (word != null)
                    word.deleteFromRealm()
                else
                    isWordExisted = false
            }
            realm.close()
            if (!isWordExisted)
                "Не найдено слово для удаления!"
            "Слово удалено из словаря!"
        } else
            "Для удаления слова из словаря нужно заполнить поле Слово!"
    }

    suspend fun getWordsFromRealm(findWord: String, fieldName: String, cutList: Boolean, ): List<WordForView> {
        return if (isRealmClosed) {
            isRealmClosed = false
            val listWordsForView = ArrayList<WordForView>()
            realm = Realm.getInstance(config)
            realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                val listWords = if (findWord.isEmpty() || cutList)
                    realmTransaction.where(Word::class.java).sort(fieldName).findAll()
                else
                    realmTransaction.where(Word::class.java)
                        .like(fieldName, findWord, Case.INSENSITIVE).sort(fieldName).findAll()
                if (listWords.isNotEmpty()) {
                    for (word in listWords)
                        listWordsForView.add(WordForView(word.original, word.translation))
                }
            }
            realm.close()
            isRealmClosed = true
            listWordsForView
        } else
            emptyList()
    }
}

data class WordForView(
    var original: String,
    var translation: String,
)