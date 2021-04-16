package com.korobeynikov.maxlevelsdifficulty

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var jsonArray: JSONArray
    val retrofit=Retrofit.Builder().baseUrl("http://api.steampowered.com/ISteamUserStats/GetGlobalAchievementPercentagesForApp/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val achievementApi=retrofit.create(AchievementApi::class.java)
    var db=DB.getInstance().getDatabase()
    val achievementDao=db.achieventDao()
    private val scope=CoroutineScope(Job())

    private val REQUEST_CODE_PERMISSION_WRITE_STORAGE=1
    private val REQUEST_CODE_PERMISSION_READ_STORAGE=2

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_PERMISSION_WRITE_STORAGE->
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    val directory=File(Environment.getExternalStorageDirectory().absolutePath,"Max levels difficulty")
                    if(!directory.exists())
                        directory.mkdirs()
                    val achievementFile=File(directory.path+"/achievements.txt")
                    scope.launch{
                        val achievementList=achievementDao.getSortedAll()
                        if(achievementList!=null){
                            try{
                                val writer = BufferedWriter(FileWriter(achievementFile))
                                for (i in achievementList) {
                                    writer.write("" + i.idGame + ";" + i.nameGame + ";" + i.nameAchievement + ";" + i.percent)
                                    writer.newLine()
                                }
                                writer.close()
                                launch(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity,"Сохранение в файл успешно завершено!",Toast.LENGTH_SHORT).show()
                                }
                            }
                            catch(e:IOException){
                                launch(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity,"Произошла ошибка при сохранении в файл!",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else
                            launch(Dispatchers.Main){
                                Toast.makeText(this@MainActivity,"Нет сохраненных данных!",Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                else
                    Toast.makeText(this@MainActivity,"Разрешение на сохранение данных получить не удалось!",Toast.LENGTH_SHORT).show()
            REQUEST_CODE_PERMISSION_READ_STORAGE->
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    val directory=File(Environment.getExternalStorageDirectory().absolutePath,"Max levels difficulty")
                    val achievementFile=File(directory.path+"/achievements.txt")
                    try{
                        var haveAchievements=false
                        val reader=BufferedReader(FileReader(achievementFile))
                        var line=reader.readLine()
                        scope.launch{
                            while(line!=null){
                                val idGame=line.split(";")[0].toLong()
                                var achievement=achievementDao.getAchievementById(idGame)
                                if(achievement==null){
                                    achievement=Achievement()
                                    achievement.idGame=idGame
                                    achievement.nameGame=line.split(";")[1]
                                    achievement.nameAchievement=line.split(";")[2]
                                    achievement.percent=line.split(";")[3].toDouble()
                                    achievementDao.insert(achievement)
                                    if(!haveAchievements)haveAchievements=true
                                }
                                line=reader.readLine()
                            }
                            updateResultText(achievementDao)
                            if(haveAchievements)
                                launch(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity, "Данные из файла успешно загружены!", Toast.LENGTH_SHORT).show()
                                }
                            else
                                launch(Dispatchers.Main){
                                    Toast.makeText(this@MainActivity, "В файле не обнаружено данных для загрузки!", Toast.LENGTH_SHORT).show()
                                }
                            reader.close()
                        }
                    }
                    catch(e:IOException){
                        Toast.makeText(this,"Произошла ошибка при загрузке данных из файла!",Toast.LENGTH_SHORT).show()
                    }
                }
                else
                    Toast.makeText(this@MainActivity,"Разрешение на загрузку данных получить не удалось!",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,1,0,"Сохранить данные в файл")
        menu?.add(0,2,0,"Загрузить данные из файла")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            1->ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
            2->ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateResultText(achievementDao:AchievementDao){
        scope.launch{
            var text=""
            var sortListAchievements = achievementDao.getSortedAll()
            for (i in sortListAchievements) {
                var achievements = achievementApi.getListAchievements(i.idGame!!.toLong())
                achievements.enqueue(object : Callback<ListAchievements> {
                    override fun onResponse(call: Call<ListAchievements>, response: Response<ListAchievements>){
                        if (response.isSuccessful) {
                            val json = response.body()?.achievementpercentages.toString()
                            val jsonObj = JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1))
                            jsonArray = (jsonObj["achievements"] as JSONArray)
                            var j = 0
                            while (j < jsonArray.length()) {
                                if ("" + jsonArray.getJSONObject(j).get("name") == i.nameAchievement){
                                    launch {
                                        var achievement = achievementDao.getAchievementById(i.idGame!!.toLong())
                                        achievement.percent = jsonArray.getJSONObject(j).get("percent").toString().toDouble()
                                        achievementDao.update(achievement)
                                    }
                                }
                                j++
                            }
                        }
                        else
                            launch(Dispatchers.Main){
                                Toast.makeText(this@MainActivity,"Игра по данному ID не найдена!",Toast.LENGTH_SHORT).show()
                            }
                    }

                    override fun onFailure(call: Call<ListAchievements>, t: Throwable) {
                        launch(Dispatchers.Main){
                            Toast.makeText(this@MainActivity,"Не удалось подключиться к серверу!",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            //Отображение отсортированного списка достижений
            sortListAchievements=achievementDao.getSortedAll()
            for(i in sortListAchievements)
                text+=i.nameGame+"="+i.percent+"\n"
            
            launch(Dispatchers.Main){
                resultText.text=text
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Вывод данных по достижениям
        updateResultText(achievementDao)
        //Поиск достижений для указанной игры
        btnSearch.setOnClickListener{
            if (textId.text.length > 0) {
                var achievements=achievementApi.getListAchievements(textId.text.toString().toLong())
                achievements.enqueue(object : Callback<ListAchievements> {
                    override fun onResponse(call: Call<ListAchievements>, response: Response<ListAchievements>){
                        if(response.isSuccessful) {
                            val json = response.body()?.achievementpercentages.toString()
                            val jsonObj = JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1))

                            jsonArray = (jsonObj["achievements"] as JSONArray)
                            var achievements: Array<String> = Array(jsonArray.length(), { "" })
                            var i = 0
                            while (i < jsonArray.length()) {
                                achievements[i] = "" + jsonArray.getJSONObject(i).get("name")
                                i++
                            }
                            var adapterAchievements = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_spinner_item, achievements)
                            adapterAchievements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            listAchievements.adapter = adapterAchievements
                        }
                        else
                            Toast.makeText(this@MainActivity, "Игра по данному ID не найдена!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ListAchievements>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Не удалось подключиться к серверу!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        //Получение процента для выбранного достижения
        listAchievements.onItemSelectedListener=this
        //Добавить достижение
        btnAdd.setOnClickListener{
            val nameGame=textNameGame.text
            val textAchievement=listAchievements.selectedItem
            val id=textId.text
            val percent=textPercent.text
            if(nameGame.isEmpty())
                Toast.makeText(this, "Не заполнено поле Название игры!", Toast.LENGTH_SHORT).show()
            else if(textAchievement.toString().isEmpty())
                Toast.makeText(this, "Не указано достижение!", Toast.LENGTH_SHORT).show()
            else if(id.isEmpty())
                Toast.makeText(this, "Не заполнено поле ID игры!", Toast.LENGTH_SHORT).show()
            else{
                scope.launch {
                    var achievement=achievementDao.getAchievementById(id.toString().toLong())
                    if(achievement==null){
                        achievement=achievementDao.getAchievementByName(""+nameGame)
                        if(achievement==null) {
                            achievement = Achievement()
                            achievement.idGame = id.toString().toLong()
                            achievement.nameGame = "" + nameGame
                            achievement.nameAchievement = "" + textAchievement
                            achievement.percent = percent.toString().split(":")[1].replace(',', '.').toDouble()
                            achievementDao.insert(achievement)
                            updateResultText(achievementDao)
                        }
                        else
                            launch(Dispatchers.Main){
                                Toast.makeText(this@MainActivity,"Игра с данным названием уже добавлена!",Toast.LENGTH_SHORT).show()
                            }
                    }
                    else
                        launch(Dispatchers.Main){
                            Toast.makeText(this@MainActivity,"Игра с данным ID уже добавлена!",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
        //Изменить достижение
        btnChange.setOnClickListener{
            val nameGame=textNameGame.text
            val textAchievement=listAchievements.selectedItem
            val id=textId.text
            if(!id.isEmpty()) {
                scope.launch {
                    var achievement=achievementDao.getAchievementById(id.toString().toLong())
                    if(achievement!=null) {
                        if(textAchievement!=null) {
                            achievement.nameAchievement = "" + textAchievement
                            achievement.percent=textPercent.text.split(":")[1].replace(',','.').toDouble()
                        }
                        if (!nameGame.isEmpty())
                            achievement.nameGame = "" + nameGame
                        achievementDao.update(achievement)
                        updateResultText(achievementDao)
                    }
                    else
                        launch(Dispatchers.Main){
                            Toast.makeText(this@MainActivity,"Игра по данному ID не найдена!",Toast.LENGTH_SHORT).show()
                        }
                }
            }
            else
                Toast.makeText(this, "Не заполнено поле ID игры!", Toast.LENGTH_SHORT).show()
        }
        //Удалить достижение
        btnDelete.setOnClickListener{
            val id=textId.text
            scope.launch {
                var achievement=achievementDao.getAchievementById(id.toString().toLong())
                if(achievement!=null) {
                    achievementDao.delete(achievement)
                    updateResultText(achievementDao)
                }
                else
                    launch(Dispatchers.Main){
                        Toast.makeText(this@MainActivity,"Игра по данному ID не найдена!",Toast.LENGTH_SHORT).show()
                    }
            }
        }
        //
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var percent=jsonArray.getJSONObject(listAchievements.selectedItemPosition).get("percent")
        percent=String.format("%.1f",percent)
        textPercent.setText("Percent: "+percent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}