package ru.korobeynikov.databasegames

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.databasegames.databinding.ActivityMainBinding
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by inject()

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    }

    var numOperation = 0

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> {
                mainViewModel.saveGamesInFile(Environment.getExternalStorageDirectory().absolutePath)
                Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
            }
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                mainViewModel.loadGamesFromFile(Environment.getExternalStorageDirectory().absolutePath)
                Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        startKoin {
            androidContext(this@MainActivity)
            modules(mainViewModelModule)
        }
        mainViewModel.getGames()
        mainViewModel.listGames.observe(this, { games ->
            val allGames = StringBuilder()
            for (i in games.indices) {
                val game = games[i].split(";")
                allGames.append(" ${i + 1}. ${game[0]} (${game[2]}) = ${game[1]}\n")
            }
            binding.showGamesText.text = allGames.toString()
        })
    }

    fun addGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        if (nameGameText.isEmpty() || ratingGameText.isEmpty() || yearGameText.isEmpty() || genreGame == 0)
            Toast.makeText(this, "Для добавления игры необходимо заполнить поля Название, Оценка, Год и указать Жанр!"
                , Toast.LENGTH_LONG).show()
        else if (ratingGameText.toInt() < 1 || ratingGameText.toInt() > 10)
            Toast.makeText(this, "Некорректно указана оценка. Оценка должна принимать значение в интервале от 1 до 10."
                , Toast.LENGTH_LONG).show()
        else {
            mainViewModel.addGame(nameGameText, ratingGameText, yearGameText, genreGame)
            Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
        }
    }

    fun changeGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        if (nameGameText.isEmpty())
            Toast.makeText(this, "Для изменения данных об игре необходимо заполнить поле Название!"
                , Toast.LENGTH_LONG).show()
        else if (ratingGameText.toInt() < 1 || ratingGameText.toInt() > 10)
            Toast.makeText(this, "Некорректно указана оценка. Оценка должна принимать значение в интервале от 1 до 10."
                , Toast.LENGTH_LONG).show()
        else {
            mainViewModel.changeGame(nameGameText, ratingGameText, yearGameText, genreGame)
            Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
        }
    }

    fun deleteGame() {
        val nameGameText = binding.nameGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        if (nameGameText.isEmpty())
            Toast.makeText(this, "Для удаления игры необходимо заполнить поле Название!", Toast.LENGTH_LONG).show()
        else {
            mainViewModel.deleteGame(nameGameText, yearGameText)
            Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
        }
    }

    fun saveGamesInFile() {
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

    fun loadGamesFromFile() {
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

    fun showGames() {
        val nameGameText = binding.nameGameText.text.toString()
        val ratingGameText = binding.ratingGameText.text.toString()
        val yearGameText = binding.yearGameText.text.toString()
        val genreGame = binding.genreGameList.selectedItemPosition
        val isSort = binding.isSort.isChecked
        mainViewModel.getGames(nameGameText, ratingGameText, yearGameText, genreGame, isSort)
    }

    fun clearAllFields() {
        binding.nameGameText.text.clear()
        binding.ratingGameText.text.clear()
        binding.yearGameText.text.clear()
        binding.genreGameList.setSelection(0)
    }

    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE) {
                mainViewModel.saveGamesInFile(Environment.getExternalStorageDirectory().absolutePath)
                Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
            } else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                mainViewModel.loadGamesFromFile(Environment.getExternalStorageDirectory().absolutePath)
                Toast.makeText(this, mainViewModel.message, Toast.LENGTH_LONG).show()
            }
        }
}