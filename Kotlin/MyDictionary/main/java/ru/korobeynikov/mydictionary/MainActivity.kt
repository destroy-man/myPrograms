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

    //Обработка разрешений (для старых версий)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> mainPresenter.saveWords(path)
            REQUEST_CODE_PERMISSION_READ_STORAGE -> mainPresenter.loadWords(path)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                fieldName = "original"
                val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                    cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        })
        //Фильтр по введенным символам в поле Перевод
        translationText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                fieldName = "translation"
                val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                    cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        })
        //Ограничение на количество слов
        countWordsText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                val allWords = mainPresenter.getFilterListWords(findWord, fieldName,
                    cutList.isChecked, showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        })
        //Добавление одного слова
        addWord.setOnClickListener {
            if (originalText.text.isEmpty() || translationText.text.isEmpty())
                showMessage("Для добавления слова в словарь нужно заполнить поля Слово и Перевод!")
            else
                mainPresenter.addWord(originalText.text.toString(), translationText.text.toString())
        }
        //Удаление одного слова
        deleteWord.setOnClickListener {
            if (originalText.text.isEmpty())
                showMessage("Для удаления слова из словаря нужно заполнить поле Слово!")
            else
                mainPresenter.deleteWord(originalText.text.toString())
        }
        //Сохранение слов в файл
        saveWordsInFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        }
        //Загрузка слов из файла
        loadWordsFromFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                    .READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            val allWords = mainPresenter.getFilterListWords(findWord, fieldName, cutList.isChecked,
                showTranslation.isChecked, countWordsText.text.toString())
            showListWords(allWords)
        }
        //Обрезание списка слов
        cutList.setOnClickListener {
            val allWords = mainPresenter.getFilterListWords(findWord, fieldName, cutList.isChecked,
                showTranslation.isChecked, countWordsText.text.toString())
            showListWords(allWords)
        }
    }

    //Получение и отображение списка слов
    fun showListWords(allWords: StringBuilder) {
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

    //Объект для работы после получения разрешения на работу с хранилищем данных (для новых версий)
    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                mainPresenter.saveWords(path)
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                mainPresenter.loadWords(path)
            }
        }

    //Вывод сообщения
    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }
}