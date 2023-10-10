package ru.korobeynikov.mydictionary

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import ru.korobeynikov.daggercomponents.MyDictionaryApp
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1 //код разрешения для записи данных
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2 //код разрешения для чтения данных
    }

    private var numOperation = 0 //номер операции (сохранение данных-1, загрузка данных-2)
    private val path = Environment.getExternalStorageDirectory().absolutePath //путь к файлу
    private var findWord = "" //слово для фильтрации списка
    private var fieldName = "original" //столбец по которому будет происходить фильтрация списка

    @Inject
    lateinit var mainPresenter: MainPresenter //объект Presenter

    //Обработка разрешений
    private fun checkPermissions(permissions: Array<String>) = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (grantResults.isNotEmpty()) {
            val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (allPermissionsGranted) {
                when (requestCode) {
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE -> mainPresenter.saveWords(path)
                    REQUEST_CODE_PERMISSION_READ_STORAGE -> mainPresenter.loadWords(path)
                }
            } else {
                Toast.makeText(this,
                    "Необходимо получить разрешения на чтение и запись!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun processPermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(String.format("package:%s", this.packageName))
            launcherManageStorage.launch(intent)
        } catch (e: Exception) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            launcherManageStorage.launch(intent)
        }
    }

    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                mainPresenter.saveWords(path)
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                mainPresenter.loadWords(path)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Инициализация realm, данных и отображение всего списка слов
        Realm.init(this)
        (application as MyDictionaryApp).myDictionaryComponent.injectMainActivity(this)
        mainPresenter.attachView(this)
        mainPresenter.getListWords()
        //Фильтр по введенным символам в поле Слово
        originalText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                if (findWord.indexOf('(') != -1)
                    findWord = findWord.substring(findWord.indexOf('(') - 2)
                fieldName = "original"
                if (haveParameters()) {
                    val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                        cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                    showListWords(allWords)
                } else
                    showListWords(StringBuilder())
            }
        })
        //Фильтр по введенным символам в поле Перевод
        translationText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                fieldName = "translation"
                if(haveParameters()) {
                    val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                        cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                    showListWords(allWords)
                } else
                    showListWords(StringBuilder())
            }
        })
        //Ограничение на количество слов
        countWordsText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if(haveParameters()) {
                    val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                        cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                    showListWords(allWords)
                } else
                    showListWords(StringBuilder())
            }
        })
        //Добавление одного слова
        addWord.setOnClickListener {
            if (originalText.text.isEmpty())
                showMessage("Для добавления слова в словарь нужно заполнить поле Слово!")
            else {
                if (originalText.text.toString().indexOf('(') == -1 && translationText.text.isEmpty())
                    showMessage("Для добавления слова в словарь нужно заполнить поле Перевод!")
                else {
                    var originalWord = originalText.text.toString()
                    var transcriptionWord = ""
                    if (originalWord.indexOf('(') != -1) {
                        val begin = originalWord.indexOf('(')
                        val end = originalWord.indexOf(')')
                        transcriptionWord = originalWord.substring(begin + 1, end)
                        originalWord = originalWord.substring(0, begin - 1)
                    }
                    mainPresenter.addWord(originalWord, transcriptionWord, translationText.text.toString())
                }
            }
        }
        //Удаление одного слова
        deleteWord.setOnClickListener {
            if (originalText.text.isEmpty())
                showMessage("Для удаления слова из словаря нужно заполнить поле Слово!")
            else {
                var originalWord = originalText.text.toString()
                if (originalWord.indexOf('(') != -1) {
                    val begin = originalWord.indexOf('(')
                    originalWord = originalWord.substring(0, begin - 1)
                }
                mainPresenter.deleteWord(originalWord)
            }
        }
        //Сохранение слов в файл
        saveWordsInFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager())
                    mainPresenter.saveWords(path)
                else
                    processPermission()
            } else {
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (checkPermissions(permissions))
                    mainPresenter.saveWords(path)
                else
                    ActivityCompat.requestPermissions(this@MainActivity, permissions,
                        REQUEST_CODE_PERMISSION_WRITE_STORAGE)
            }
        }
        //Загрузка слов из файла
        loadWordsFromFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager())
                    mainPresenter.loadWords(path)
                else
                    processPermission()
            } else {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                if (checkPermissions(permissions))
                    mainPresenter.loadWords(path)
                else
                    ActivityCompat.requestPermissions(this@MainActivity, permissions,
                        REQUEST_CODE_PERMISSION_READ_STORAGE)
            }
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            if(haveParameters()) {
                val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                    cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            } else
                showListWords(StringBuilder())
        }
        //Обрезание списка слов
        cutList.setOnClickListener {
            if(haveParameters()) {
                val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                    cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            } else
                showListWords(StringBuilder())
        }
    }

    //Получение и отображение списка слов
    fun showListWords(allWords: StringBuilder) {
        showWordsText.text = allWords.toString()
        countWords.text = getString(R.string.countWords, allWords.split("\n").size - 1)
    }

    //Вывод сообщения
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    //Проверка на наличия параметров для поиска в БД
    fun haveParameters() = !(findWord.isEmpty() && countWordsText.text.toString().isEmpty())

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}