package ru.korobeynikov.databasegames

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.databasegames.databinding.ActivityMainBinding
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1 //код разрешения для записи данных
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2 //код разрешения для чтения данных
    }

    private var numOperation = 0 //номер операции (сохранение данных-1, загрузка данных-2)
    private var path = Environment.getExternalStorageDirectory().absolutePath //путь к файлу
    private lateinit var binding: ActivityMainBinding //биндинг для работы с элементами формы
    private val mainViewModel: MainViewModel by inject() //объект ViewModel

    //Обработка разрешений (для старых версий)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> mainViewModel.saveGamesInFile(path)
            REQUEST_CODE_PERMISSION_READ_STORAGE -> mainViewModel.loadGamesFromFile(path)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Инициализация данных
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        startKoin {
            androidContext(this@MainActivity)
            modules(mainViewModelModule)
        }
        //Получение и отображение данных по играм
        mainViewModel.getGames()
        mainViewModel.gamesLiveData.observe(this) { games ->
            val allGames = StringBuilder()
            for (i in games.indices) {
                val game = games[i]
                allGames.append(" ${i + 1}. ${game.name} (${game.year}) = ${game.rating}\n")
            }
            binding.showGamesText.text = allGames.toString()
        }
        //Вывод сообщения
        mainViewModel.messageLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    //Добавление игры
    fun addGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        if (nameGameText.isEmpty() || ratingGameText.isEmpty() || yearGameText.isEmpty() || genreGame == 0)
            Toast.makeText(this, "Для добавления игры необходимо заполнить поля " +
                    "Название, Оценка, Год и указать Жанр!", Toast.LENGTH_LONG).show()
        else if (ratingGameText.toInt() < 1 || ratingGameText.toInt() > 10)
            Toast.makeText(this, "Некорректно указана оценка. Оценка должна принимать " +
                    "значение в интервале от 1 до 10.", Toast.LENGTH_LONG).show()
        else
            mainViewModel.addGame(nameGameText, ratingGameText, yearGameText, genreGame)
    }

    //Изменение параметров игры
    fun changeGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        if (nameGameText.isEmpty())
            Toast.makeText(this, "Для изменения данных об игре необходимо заполнить " +
                    "поле Название!", Toast.LENGTH_LONG).show()
        else if (ratingGameText.isNotEmpty() && (ratingGameText.toInt() < 1 || ratingGameText.toInt() > 10))
            Toast.makeText(this, "Некорректно указана оценка. Оценка должна принимать " +
                    "значение в интервале от 1 до 10.", Toast.LENGTH_LONG).show()
        else
            mainViewModel.changeGame(nameGameText, ratingGameText, yearGameText, genreGame)
    }

    //Удаление игры
    fun deleteGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        if (nameGameText.isEmpty())
            Toast.makeText(this, "Для удаления игры необходимо заполнить поле " +
                    "Название!", Toast.LENGTH_LONG).show()
        else
            mainViewModel.deleteGame(nameGameText, yearGameText)
    }

    //Сохранение игр в файл
    fun saveGamesInFile() {
        numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            processPermission()
        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                .WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
    }

    //Загрузка игр из файла
    fun loadGamesFromFile() {
        numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            processPermission()
        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest
                .permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
    }

    //Обработка разрешений (для новых версий)
    @RequiresApi(Build.VERSION_CODES.R)
    fun processPermission() {
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
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE)
                mainViewModel.saveGamesInFile(path)
            else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE)
                mainViewModel.loadGamesFromFile(path)
        }

    //Фильтрация списка игр
    fun showGames() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        val isSort = binding.isSort.isChecked
        mainViewModel.filterGames(nameGameText, ratingGameText, yearGameText, genreGame, isSort)
    }

    //Очистка всех полей
    fun clearAllFields() {
        binding.nameGameText.text.clear()
        binding.ratingGameText.text.clear()
        binding.yearGameText.text.clear()
        binding.genreGameList.setSelection(0)
    }
}