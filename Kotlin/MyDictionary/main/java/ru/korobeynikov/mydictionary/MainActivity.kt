package ru.korobeynikov.mydictionary

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.bson.types.ObjectId
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var config: RealmConfiguration
    lateinit var realm: Realm
    val scope = CoroutineScope(Job())
    private val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
    private val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE = 3
    var numOperation = 0
    var isRealmClosed = true
    var isCutList = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 1, 0, "Обрезать список по фильтру")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                scope.launch {
                    isCutList = true
                    if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                        showOriginalAndTranslation("", "original")
                    else
                        showOriginalAndTranslation("", "translation")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> writeFile()
            REQUEST_CODE_PERMISSION_READ_STORAGE -> readFile()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun writeFile() {
        val directory = File(Environment.getExternalStorageDirectory().absolutePath, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        scope.launch {
            val allWords = StringBuilder()
            realm = Realm.getInstance(config)
            realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                val listWords: List<Word>
                listWords = realmTransaction.where(Word::class.java).sort("original").findAll()
                if (listWords.isNotEmpty())
                    for (word in listWords)
                        allWords.append("${word.original}=${word.translation}\n")
            }
            realm.close()
            scope.launch {
                if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                    showOriginalAndTranslation("*${originalText.text}*", "original")
                else
                    showOriginalAndTranslation("*${translationText.text}*", "translation")
            }
            if (allWords.isNotEmpty())
                try {
                    val writer = BufferedWriter(FileWriter(fileDictionary))
                    writer.write(allWords.toString())
                    writer.close()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Сохранение в файл успешно завершено!"
                            , Toast.LENGTH_LONG).show()
                    }
                } catch (e: IOException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Произошла ошибка при сохранении в файл!"
                            , Toast.LENGTH_LONG).show()
                    }
                }
            else
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Нет данных для сохранения!"
                        , Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun readFile() {
        val directory = File(Environment.getExternalStorageDirectory().absolutePath, "My dictionary")
        if (!directory.exists())
            directory.mkdirs()
        val fileDictionary = File(directory, "List words.txt")
        scope.launch {
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
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Произошла ошибка при загрузке данных из файла!"
                        , Toast.LENGTH_LONG).show()
                }
            }
            if (allWords.isNotEmpty()) {
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
                scope.launch {
                    if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                        showOriginalAndTranslation("*${originalText.text}*", "original")
                    else
                        showOriginalAndTranslation("*${translationText.text}*", "translation")
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Данные из файла успешно загружены!"
                        , Toast.LENGTH_LONG).show()
                }
            } else
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "В файле не обнаружено данных для загрузки!"
                        , Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Инициализация realm и отображение всего списка слов
        Realm.init(this)
        config = RealmConfiguration.Builder().schemaVersion(1L).build()
        scope.launch {
            showOriginalAndTranslation("", "original")
        }
        //Фильтр по введенным символам
        originalText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (isRealmClosed)
                    scope.launch {
                        showOriginalAndTranslation("*${text.toString()}*", "original")
                    }
            }
        })
        translationText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (isRealmClosed)
                    scope.launch {
                        showOriginalAndTranslation("*${text.toString()}*", "translation")
                    }
            }
        })
        //Добавление одного слова
        addWord.setOnClickListener {
            val originalText = originalText.text.toString()
            val translationText = translationText.text.toString()
            scope.launch {
                realm = Realm.getInstance(config)
                realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
                    val word = realmTransaction.where(Word::class.java)
                        .equalTo("original", originalText).findFirst()
                    if (word != null) {
                        word.translation = translationText
                        realmTransaction.copyToRealmOrUpdate(word)
                    } else
                        realmTransaction.copyToRealmOrUpdate(Word(ObjectId().toHexString()
                            , originalText, translationText))
                }
                realm.close()
                showOriginalAndTranslation("*${originalText}*", "original")
            }
        }
        //Удаление слова по введенному слову в поле Слово
        deleteWord.setOnClickListener {
            val originalText = originalText.text.toString()
            scope.launch {
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
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Не найдено слово для удаления!"
                            , Toast.LENGTH_LONG).show()
                    }
                showOriginalAndTranslation("*${originalText}*", "original")
            }
        }
        //Сохранение слов в файл
        saveWordsInFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(String.format("package:%s", this.packageName))
                    this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                }
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    , REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        }
        //Загрузка слов из файла
        loadWordsFromFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(String.format("package:%s", this.packageName))
                    this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                }
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    , REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            scope.launch {
                if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                    showOriginalAndTranslation("*${originalText.text}*", "original")
                else
                    showOriginalAndTranslation("*${translationText.text}*", "translation")
            }
        }
    }

    suspend fun showOriginalAndTranslation(findWord: String, fieldName: String) {
        isRealmClosed = false
        val allWords = StringBuilder()
        realm = Realm.getInstance(config)
        realm.executeTransactionAwait(Dispatchers.Default) { realmTransaction ->
            var listWords: List<Word> = if (findWord.isEmpty())
                realmTransaction.where(Word::class.java).sort(fieldName).findAll()
            else
                realmTransaction.where(Word::class.java).like(fieldName, findWord, Case.INSENSITIVE)
                    .sort(fieldName).findAll()
            if (listWords.isNotEmpty())
                if (isCutList) {
                    val indexCut =
                        if (fieldName == "original")
                            listWords.indexOf(listWords.find { word -> word.original.startsWith(originalText.text) })
                        else
                            listWords.indexOf(listWords.find { word -> word.translation.startsWith(translationText.text) })
                    listWords = listWords.subList(indexCut, listWords.lastIndex + 1)
                    isCutList = false
                }
            for (word in listWords) {
                if (showTranslation.isChecked) {
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
        realm.close()
        withContext(Dispatchers.Main) {
            showWordsText.text = allWords.toString()
            countWords.text = "Слов: ${allWords.split("\n").size - 1}"
        }
        isRealmClosed = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PERMISSION_MANAGE_STORAGE) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) writeFile()
            else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) readFile()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}