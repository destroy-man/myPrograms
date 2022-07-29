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
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.korobeynikov.myachievements.R
import ru.korobeynikov.myachievements.databinding.ActivityMainBinding
import java.io.IOException
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MviView {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var currentDate: Calendar

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    }

    private val path = Environment.getExternalStorageDirectory().absolutePath
    var numOperation = 0

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> mainPresenter.handleIntent(MainIntent.saveAchievements(path))
            REQUEST_CODE_PERMISSION_READ_STORAGE ->
                try {
                    mainPresenter.handleIntent(MainIntent.loadAchievements(path))
                    Toast.makeText(this, "Данные из файла успешно загружены!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Toast.makeText(this, "Произошла ошибка при загрузке данных из файла!",
                        Toast.LENGTH_SHORT).show()
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainPresenter.states.observeOn(AndroidSchedulers.mainThread()).subscribe { state ->
            render(state)
        }

        mainPresenter.handleIntent(MainIntent.getAllAchievements(""))

        RxView.clicks(binding.btnSearch).subscribe {
            val idGame = binding.textId.text.toString()
            if (idGame.isNotEmpty()) {
                mainPresenter.handleIntent(MainIntent.searchAchievements(idGame.toLong()))
            }
        }
        RxAdapterView.itemSelections(binding.listAchievements).subscribe { pos ->
            if (pos > -1) {
                val nameAchievement = binding.listAchievements.getItemAtPosition(pos).toString()
                mainPresenter.handleIntent(MainIntent.getPercentForAchievement(nameAchievement))
            }
        }
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
        RxTextView.textChanges((binding.textNameGame))
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribe { game ->
                mainPresenter.handleIntent(MainIntent.getAllAchievements(game.toString()))
            }
        RxView.clicks(binding.btnAdd).subscribe {
            val idGame = binding.textId.text.toString()
            val nameAchievement = binding.listAchievements.selectedItem
            val percentAchievement = binding.textPercent.text.toString()
            val nameGame = binding.textNameGame.text.toString()
            val dateAchievement = binding.textDate.text.toString()
            val timeAchievement = binding.textTime.text.toString()
            when {
                idGame.isEmpty() ->
                    Toast.makeText(this, "Не заполнено поле ID игры!", Toast.LENGTH_LONG).show()
                nameAchievement == null ->
                    Toast.makeText(this, "Не указано достижение!", Toast.LENGTH_LONG).show()
                nameGame.isEmpty() ->
                    Toast.makeText(this, "Не заполнено поле Название игры!", Toast.LENGTH_LONG).show()
                dateAchievement.isEmpty() ->
                    Toast.makeText(this, "Не заполнено поле Дата получения достижения!",
                        Toast.LENGTH_LONG).show()
                timeAchievement.isEmpty() ->
                    Toast.makeText(this, "Не заполнено поле Время получения достижения!",
                        Toast.LENGTH_LONG).show()
                else ->
                    mainPresenter.handleIntent(MainIntent.addAchievement(nameGame, nameAchievement.toString(),
                        idGame.toLong(), percentAchievement.substring(9).toDouble(),
                        dateAchievement, timeAchievement))
            }
        }
        RxView.clicks(binding.btnChange).subscribe {
            val idGame = binding.textId.text.toString()
            val nameAchievement = binding.listAchievements.selectedItem
            val percentAchievement = binding.textPercent.text.toString()
            val nameGame = binding.textNameGame.text.toString()
            val dateAchievement = binding.textDate.text.toString()
            val timeAchievement = binding.textTime.text.toString()
            if (idGame.isEmpty())
                Toast.makeText(this, "Не заполнено поле ID игры!", Toast.LENGTH_LONG).show()
            else
                mainPresenter.handleIntent(MainIntent.changeAchievement(nameGame, nameAchievement, idGame.toLong(),
                    percentAchievement, dateAchievement, timeAchievement))
        }
        RxView.clicks(binding.btnDelete).subscribe {
            val idGame = binding.textId.text.toString()
            if (idGame.isEmpty())
                Toast.makeText(this, "Не заполнено поле ID игры!", Toast.LENGTH_LONG).show()
            else
                mainPresenter.handleIntent(MainIntent.deleteAchievement(idGame.toLong()))
        }
        RxView.clicks(binding.btnSave).subscribe {
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
        RxView.clicks(binding.btnLoad).subscribe {
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
        RxView.clicks(binding.btnUpdate).subscribe {
            try {
                mainPresenter.handleIntent(MainIntent.updateAllAchievements)
                Toast.makeText(this, "Данные успешно обновлены!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Не удалось обновить данные!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun render(state: MainState) {
        val achievementsFromDB = StringBuilder()
        for (achievement in state.achievementsFromDB)
            achievementsFromDB.append(" ${achievement.nameGame} (${achievement.idGame}) = ${achievement.percent}\n")
        binding.resultText.text = achievementsFromDB.toString()
        when (state.loading) {
            LoadingObject.achievements -> {
                val adapterAchievements = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, state.achievementsFromGame.keys.toList())
                adapterAchievements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.listAchievements.adapter = adapterAchievements
            }
            LoadingObject.percent -> binding.textPercent.text =
                "${resources.getString(R.string.titlePercent)} ${state.achievementPercent}"
            LoadingObject.message -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherManageStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE)
                mainPresenter.handleIntent(MainIntent.saveAchievements(path))
            else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE) {
                try {
                    mainPresenter.handleIntent(MainIntent.loadAchievements(path))
                    Toast.makeText(this, "Данные из файла успешно загружены!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Toast.makeText(this, "Произошла ошибка при загрузке данных из файла!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
}