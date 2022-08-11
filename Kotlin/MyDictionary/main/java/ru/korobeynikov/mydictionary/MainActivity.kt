package ru.korobeynikov.mydictionary

import android.Manifest
import android.content.Intent
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
import kotlinx.coroutines.*
import ru.korobeynikov.daggercomponents.MyDictionaryApp
import java.io.IOException
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1 //код разрешения для записи данных
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2 //код разрешения для чтения данных
    }

    private var numOperation = 0 //номер операции (сохранение данных-1, загрузка данных-2)
    private var findWord = "" //слово для фильтрации списка
    private var fieldName = "original" //столбец по которому будет происходить фильтрация списка
    private val path = Environment.getExternalStorageDirectory().absolutePath //путь к файлу со словами

    @Inject
    lateinit var scope: CoroutineScope //scope корутины для работы с базой данных

    @Inject
    lateinit var mainPresenter: MainPresenter //presenter, где происходит бизнес-логика приложения

    //Обработка разрешений (для старых версий)
    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> {
                saveWords()
            }
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                loadWords()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Инициализация realm, данных и отображение всего списка слов
        Realm.init(this)
        (application as MyDictionaryApp).myDictionaryComponent.injectMainActivity(this)
        scope.launch {
            mainPresenter.getListWords()
            withContext(Dispatchers.Main) {
                showListWords()
            }
        }
        //Фильтр по введенным символам в поле Слово
        originalText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                fieldName = "original"
                showListWords()
            }
        })
        //Фильтр по введенным символам в поле Перевод
        translationText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                fieldName = "translation"
                showListWords()
            }
        })
        //Ограничение на количество слов
        countWordsText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                showListWords()
            }
        })
        //Добавление одного слова
        addWord.setOnClickListener {
            if (originalText.text.isEmpty())
                Toast.makeText(this,
                    "Для добавления слова в словарь нужно заполнить поле Слово!",
                    Toast.LENGTH_LONG).show()
            else if (translationText.text.isEmpty())
                Toast.makeText(this,
                    "Для добавления слова в словарь нужно заполнить поле Перевод!",
                    Toast.LENGTH_LONG).show()
            else
                scope.launch {
                    mainPresenter.addWord(originalText.text.toString(),
                        translationText.text.toString())
                    withContext(Dispatchers.Main) {
                        showListWords()
                    }
                }
        }
        //Удаление одного слова
        deleteWord.setOnClickListener {
            if (originalText.text.isEmpty())
                Toast.makeText(this, "Для удаления слова из словаря нужно заполнить поле Слово!",
                    Toast.LENGTH_LONG).show()
            else
                scope.launch {
                    mainPresenter.deleteWord(originalText.text.toString())
                    withContext(Dispatchers.Main) {
                        showListWords()
                    }
                }
        }
        //Сохранение слов в файл
        saveWordsInFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        }
        //Загрузка слов из файла
        loadWordsFromFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            showListWords()
        }
        //Обрезание списка слов
        cutList.setOnClickListener {
            showListWords()
        }
    }

    //Получение и отображение списка слов
    private fun showListWords() {
        val allWords = mainPresenter.getFilterListWords(findWord, fieldName, cutList.isChecked,
            showTranslation.isChecked, countWordsText.text.toString())
        showWordsText.text = allWords.toString()
        countWords.text = "Слов: ${allWords.split("\n").size - 1}"
    }

    //Обработка разрешений (для новых версий)
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

    //Сохранение слов
    private fun saveWords() {
        try {
            mainPresenter.saveWords(path)
            Toast.makeText(this, "Сохранение в файл успешно завершено!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Toast.makeText(this, "Произошла ошибка при сохранении в файл!", Toast.LENGTH_LONG).show()
        }
    }

    //Загрузка слов
    private fun loadWords() {
        scope.launch {
            try {
                mainPresenter.loadWords(path)
                withContext(Dispatchers.Main) {
                    showListWords()
                    Toast.makeText(this@MainActivity, "Данные из файла успешно загружены!",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity,
                        "Произошла ошибка при загрузке данных из файла!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //Объект для обработки разрешений (для новых версий)
    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                saveWords()
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                loadWords()
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}