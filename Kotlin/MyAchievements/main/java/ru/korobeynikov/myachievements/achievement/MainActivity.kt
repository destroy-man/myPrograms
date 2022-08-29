package ru.korobeynikov.myachievements.achievement

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.myachievements.R
import ru.korobeynikov.myachievements.databinding.ActivityMainBinding
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MviView {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1 //код разрешения для записи данных
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2 //код разрешения для чтения данных
    }

    private var numOperation = 0 //номер операции (сохранение данных-1, загрузка данных-2)
    private val path = Environment.getExternalStorageDirectory().absolutePath //путь к файлу
    private lateinit var binding: ActivityMainBinding //биндинг для работы с элементами формы

    @Inject
    lateinit var mainPresenter: MainPresenter //объект Presenter

    @Inject
    lateinit var currentDate: Calendar //выбираемая пользователем дата

    //Обработка разрешений (для старых версий)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> mainPresenter
                .handleIntent(MainIntent.SaveAchievements(path))
            REQUEST_CODE_PERMISSION_READ_STORAGE -> mainPresenter.handleIntent(MainIntent
                .LoadAchievements(path))
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Инициализация данных и отображение списка достижений
        mainPresenter.attachView(this)
        mainPresenter.handleIntent(MainIntent.GetAllAchievements(""))
        //Поиск достижений для конкретной игры
        RxView.clicks(binding.btnSearch).subscribe {
            val idGame = binding.textId.text.toString()
            if (idGame.isEmpty())
                showMessage("Для поиска достижений необходимо указать ID игры!")
            else
                mainPresenter.handleIntent(MainIntent.SearchAchievements(idGame.toLong()))
        }
        //Определение процента для конкретного достижения
        RxAdapterView.itemSelections(binding.listAchievements).subscribe { pos ->
            if (pos > -1) {
                val nameAchievement = binding.listAchievements.getItemAtPosition(pos).toString()
                mainPresenter.handleIntent(MainIntent.GetPercentForAchievement(nameAchievement))
            }
        }
        //Фильтр по полю Название игры
        RxView.touches(binding.textDate).subscribe { event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val currentDay = currentDate[Calendar.DAY_OF_MONTH]
                val currentMonth = currentDate[Calendar.MONTH]
                val currentYear = currentDate[Calendar.YEAR]
                DatePickerDialog(this,
                    { datePicker, year, month, day ->
                        currentDate.set(year, month, day)
                        val selectDay = if (day < 10) "0$day" else day.toString()
                        val selectMonth = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                        binding.textDate.setText("$selectDay.$selectMonth.$year")
                    }, currentYear, currentMonth, currentDay).show()
            }
        }
        //Выбор даты
        RxView.touches(binding.textTime).subscribe { event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val currentHour = currentDate[Calendar.HOUR_OF_DAY]
                val currentMinute = currentDate[Calendar.MINUTE]
                TimePickerDialog(this,
                    { timePicker, hour, minute ->
                        currentDate.set(Calendar.HOUR_OF_DAY, hour)
                        currentDate.set(Calendar.MINUTE, minute)
                        val selectHour = if (hour < 10) "0$hour" else hour.toString()
                        val selectMinute = if (minute < 10) "0$minute" else minute.toString()
                        binding.textTime.setText("$selectHour:$selectMinute")
                    }, currentHour, currentMinute, true).show()
            }
        }
        //Выбор времени
        RxTextView.textChanges((binding.textNameGame))
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribe { game ->
                mainPresenter.handleIntent(MainIntent.GetAllAchievements(game.toString()))
            }
        //Добавление достижения
        RxView.clicks(binding.btnAdd).subscribe {
            val idGame = binding.textId.text.toString()
            val nameAchievement = binding.listAchievements.selectedItem
            val percentAchievement = binding.textPercent.text.toString()
            val nameGame = binding.textNameGame.text.toString()
            val dateAchievement = binding.textDate.text.toString()
            val timeAchievement = binding.textTime.text.toString()
            when {
                idGame.isEmpty() || nameAchievement == null || nameGame.isEmpty() ||
                        dateAchievement.isEmpty() || timeAchievement.isEmpty() ->
                    showMessage("Для добавления достижения необходимо заполнить поля ID игры, " +
                            "Достижения, Название игры, Дата получения достижения и Время получения " +
                            "достижения!")
                else ->
                    mainPresenter.handleIntent(MainIntent.AddAchievement(nameGame,
                        nameAchievement.toString(), idGame.toLong(), percentAchievement
                        .substring(9).toDouble(), dateAchievement, timeAchievement))
            }
        }
        //Изменение достижения
        RxView.clicks(binding.btnChange).subscribe {
            val idGame = binding.textId.text.toString()
            val nameAchievement = binding.listAchievements.selectedItem
            val percentAchievement = binding.textPercent.text.toString()
            val nameGame = binding.textNameGame.text.toString()
            val dateAchievement = binding.textDate.text.toString()
            val timeAchievement = binding.textTime.text.toString()
            if (idGame.isEmpty())
                showMessage("Не заполнено поле ID игры!")
            else
                mainPresenter.handleIntent(MainIntent.ChangeAchievement(nameGame, nameAchievement,
                    idGame.toLong(), percentAchievement, dateAchievement, timeAchievement))
        }
        //Удаление достижения
        RxView.clicks(binding.btnDelete).subscribe {
            val idGame = binding.textId.text.toString()
            if (idGame.isEmpty())
                showMessage("Не заполнено поле ID игры!")
            else
                mainPresenter.handleIntent(MainIntent.DeleteAchievement(idGame.toLong()))
        }
        //Сохранение достижений в файл
        RxView.clicks(binding.btnSave).subscribe {
            numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
        }
        //Загрузка достижений из файла
        RxView.clicks(binding.btnLoad).subscribe {
            numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                processPermission()
            } else
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                    .READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        //Обновление данных по достижениям
        RxView.clicks(binding.btnUpdate).subscribe {
            mainPresenter.handleIntent(MainIntent.UpdateAllAchievements)
        }
    }

    //Обновление экрана приложения
    override fun render(state: MainState) {
        val achievementsFromDB = StringBuilder()
        for (achievement in state.achievementsFromDB)
            achievementsFromDB.append(" ${achievement.nameGame} (${achievement.idGame}) = ${achievement.percent}\n")
        binding.resultText.text = achievementsFromDB.toString()
        when (state.loading) {
            LoadingObject.Achievements -> {
                val adapterAchievements = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, state.achievementsFromGame.keys.toList())
                adapterAchievements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.listAchievements.adapter = adapterAchievements
            }
            LoadingObject.Percent -> binding.textPercent.text = "${resources.getString(R.string.titlePercent)} ${state.achievementPercent}"
        }
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
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE)
                mainPresenter.handleIntent(MainIntent.SaveAchievements(path))
            else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE)
                mainPresenter.handleIntent(MainIntent.LoadAchievements(path))
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