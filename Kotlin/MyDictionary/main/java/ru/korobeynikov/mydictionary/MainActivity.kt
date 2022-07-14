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
import androidx.core.app.ActivityCompat
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.korobeynikov.daggercomponents.MyDictionaryApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    }

    var numOperation = 0
    var findWord = ""
    var fieldName = "original"
    private val path = Environment.getExternalStorageDirectory().absolutePath
    private var needWait=false //указывает закончил ли ввод пользователь

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> {
                scope.launch {
                    val allWords = mainPresenter.saveWordsInFile(path)
                    showListWords(allWords)
                }
            }
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                scope.launch {
                    val allWords = mainPresenter.loadWordsFromFile(path)
                    showListWords(allWords)
                }
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
            val allWords = mainPresenter.getListWords(findWord, fieldName, cutList.isChecked,
                showTranslation.isChecked, countWordsText.text.toString())
            showListWords(allWords)
        }
        //Фильтр по введенным символам
        originalText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                findWord = text.toString()
                fieldName = "original"
                showListWords()
            }
        })
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
            scope.launch {
                val allWords = mainPresenter.addWord(originalText.text.toString(), translationText.text.toString())
                showListWords(allWords)
            }
        }
        //Удаление слова по введенному слову в поле Слово
        deleteWord.setOnClickListener {
            scope.launch {
                val allWords = mainPresenter.deleteWord(originalText.text.toString())
                showListWords(allWords)
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
                    launcherManageStorage.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    launcherManageStorage.launch(intent)
                }
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        }
        //Загрузка слов из файла
        loadWordsFromFile.setOnClickListener {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            scope.launch {
                val allWords = mainPresenter.getListWords(findWord, fieldName, cutList.isChecked
                    , showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        }
        //Обрезание списка слов по фильтру
        cutList.setOnClickListener {
            scope.launch {
                val allWords = mainPresenter.getListWords(findWord, fieldName, cutList.isChecked
                    , showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        }
    }

    fun showListWords(){
        needWait=true
        TimeUnit.MILLISECONDS.sleep(100)
        needWait=false
        if(!needWait){
            scope.launch {
                val allWords = mainPresenter.getListWords(findWord, fieldName, cutList.isChecked,
                    showTranslation.isChecked, countWordsText.text.toString())
                showListWords(allWords)
            }
        }
    }

    private suspend fun showListWords(allWords: StringBuilder) {
        withContext(Dispatchers.Main) {
            if (mainPresenter.message.isNotEmpty())
                Toast.makeText(this@MainActivity, mainPresenter.message, Toast.LENGTH_LONG).show()
            showWordsText.text = allWords.toString()
            countWords.text = "Слов: ${allWords.split("\n").size - 1}"
        }
    }

    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                scope.launch {
                    val allWords = mainPresenter.saveWordsInFile(path)
                    showListWords(allWords)
                }
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                scope.launch {
                    val allWords = mainPresenter.loadWordsFromFile(path)
                    showListWords(allWords)
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}