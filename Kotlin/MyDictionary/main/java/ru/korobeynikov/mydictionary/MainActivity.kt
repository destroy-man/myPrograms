package ru.korobeynikov.mydictionary

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.korobeynikov.daggercomponents.MyDictionaryApp
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    }

    var numOperation = 0

    @Inject
    lateinit var config: RealmConfiguration

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var mainModel: MainModel

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> {
                scope.launch {
                    mainPresenter.saveWordsInFile()
                    showMessage(mainPresenter.message)
                    showOriginalOrTranslation()
                }
            }
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                scope.launch {
                    mainPresenter.loadWordsFromFile()
                    showMessage(mainPresenter.message)
                    showOriginalOrTranslation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Инициализация realm, данных и отображение всего списка слов
        Realm.init(this)
        (application as MyDictionaryApp).myDictionaryComponent.injectMainActivity(this)
        getAndShowWords("", "original")
        //Фильтр по введенным символам
        originalText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                getAndShowWords("*${text.toString()}*", "original")
            }
        })
        translationText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                getAndShowWords("*${text.toString()}*", "translation")
            }
        })
        //Ограничение на количество слов
        countWordsText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                showOriginalOrTranslation()
            }
        })
        //Добавление одного слова
        addWord.setOnClickListener {
            scope.launch {
                mainPresenter.addWord(originalText.text.toString(), translationText.text.toString())
                showMessage(mainPresenter.message)
                getAndShowWords("*${originalText.text}*", "original")
            }
        }
        //Удаление слова по введенному слову в поле Слово
        deleteWord.setOnClickListener {
            scope.launch {
                mainPresenter.deleteWord(originalText.text.toString())
                showMessage(mainPresenter.message)
                getAndShowWords("*${originalText.text}*", "original")
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
                    launcherManageStorage.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    launcherManageStorage.launch(intent)
                }
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    , REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Отображение перевода
        showTranslation.setOnClickListener {
            showOriginalOrTranslation()
        }
        //Обрезание списка слов по фильтру
        cutList.setOnClickListener {
            val findWord =
                if (cutList.isChecked)
                    ""
                else if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                    "*${originalText.text}*"
                else
                    "*${translationText.text}*"
            if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
                getAndShowWords(findWord, "original")
            else
                getAndShowWords(findWord, "translation")
        }
    }

    fun getAndShowWords(findWord: String, fieldName: String) {
        scope.launch {
            mainPresenter.getListWords(findWord, fieldName, cutList.isChecked)
            showListWords(mainPresenter.listWords, fieldName)
        }
    }

    fun showOriginalOrTranslation() {
        if (originalText.text.isNotEmpty() || translationText.text.isEmpty())
            getAndShowWords("*${originalText.text}*", "original")
        else
            getAndShowWords("*${translationText.text}*", "translation")
    }

    private suspend fun showListWords(listWords: List<WordForView>, fieldName: String) {
        var indexCut = 0
        val countWordShowString = countWordsText.text.toString()
        val countWordsShow =
            when {
                countWordShowString.isEmpty() -> -1
                else -> countWordShowString.toInt()
            }
        val allWords = StringBuilder()
        if (listWords.isNotEmpty()) {
            if (cutList.isChecked)
                indexCut =
                    if (fieldName == "original")
                        listWords.indexOf(listWords.find { word -> word.original.startsWith(originalText.text) })
                    else
                        listWords.indexOf(listWords.find { word -> word.translation.startsWith(translationText.text) })
            var lastIndex = indexCut + countWordsShow
            if (countWordsShow == -1 || lastIndex > listWords.size)
                lastIndex = listWords.size
            for (index in indexCut until lastIndex) {
                val word = listWords[index]
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
        withContext(Dispatchers.Main) {
            showWordsText.text = allWords.toString()
            countWords.text = "Слов: ${allWords.split("\n").size - 1}"
        }
    }

    private suspend fun showMessage(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }

    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                scope.launch {
                    mainPresenter.saveWordsInFile()
                    showMessage(mainPresenter.message)
                    showOriginalOrTranslation()
                }
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                scope.launch {
                    mainPresenter.loadWordsFromFile()
                    showMessage(mainPresenter.message)
                    showOriginalOrTranslation()
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}