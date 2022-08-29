package ru.korobeynikov.myachievements.achievement

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import ru.korobeynikov.myachievements.database.Achievement
import ru.korobeynikov.myachievements.database.AchievementDatabase
import ru.korobeynikov.myachievements.server.AchievementApi
import java.io.*

class MainRepository(db: AchievementDatabase, retrofit: Retrofit) {

    private val achievementDao = db.achievementDao() //интерфейс для работы с базой данных
    private val achievementApi = retrofit.create(AchievementApi::class.java) //интерфейс для взаимодействия с сервером

    fun getListAchievements(nameGame: String): Flowable<List<Achievement>> {
        return if (nameGame.isEmpty())
            achievementDao.getSortedAll()
        else
            achievementDao.getAchievementsByName("%$nameGame%")
    }

    fun updateAllAchievements(): Flowable<Any> {
        return Flowable.create({
            achievementDao.getSortedAll().subscribeBy { sortListAchievements ->
                for (achievement in sortListAchievements)
                    achievementApi.getListAchievements(achievement.idGame)
                        .subscribeOn(Schedulers.io())
                        .subscribeBy { listAchievements ->
                            val jsonAchievements = listAchievements.achievementpercentages.toString()
                            val jsonObj = JSONObject(jsonAchievements.substring(jsonAchievements
                                .indexOf("{"), jsonAchievements.lastIndexOf("}") + 1))
                            val jsonArray = (jsonObj["achievements"] as JSONArray)
                            var j = jsonArray.length() - 1
                            while (j > -1) {
                                if (jsonArray.getJSONObject(j).get("name") == achievement.nameAchievement) {
                                    var percent = jsonArray.getJSONObject(j).get("percent")
                                        .toString().toDouble()
                                    percent = String.format("%.1f", percent)
                                        .replace(",", ".").toDouble()
                                    if (achievement.percent != percent) {
                                        achievement.percent = percent
                                        achievementDao.update(achievement)
                                    }
                                    break
                                }
                                j--
                            }
                        }
            }
        }, BackpressureStrategy.BUFFER)
    }

    fun searchAchievements(idGame: Long): Flowable<Map<String, String>> {
        return Flowable.create({ emitter ->
            achievementApi.getListAchievements(idGame)
                .subscribeOn(Schedulers.io())
                .subscribeBy { listAchievements ->
                    val jsonAchievements = listAchievements.achievementpercentages.toString()
                    val jsonObj = JSONObject(jsonAchievements.substring(jsonAchievements
                        .indexOf("{"), jsonAchievements.lastIndexOf("}") + 1))
                    val jsonArray = (jsonObj["achievements"] as JSONArray)
                    val achievements = LinkedHashMap<String, String>()
                    for (i in 0 until jsonArray.length()) {
                        val nameAchievement = jsonArray.getJSONObject(i).get("name").toString()
                        val percent = String.format("%.1f", jsonArray.getJSONObject(i)
                            .get("percent")).replace(",", ".")
                        achievements[nameAchievement] = percent
                    }
                    emitter.onNext(achievements)
                }
        }, BackpressureStrategy.BUFFER)
    }

    fun addAchievement(nameGame: String, nameAchievement: String, idGame: Long,
                       percentAchievement: Double, dateTime: Long): Flowable<Any> {
        return Flowable.create({
            achievementDao.getAchievementById(idGame)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onComplete = {
                        val achievement = Achievement()
                        achievement.idGame = idGame
                        achievement.nameGame = nameGame
                        achievement.nameAchievement = nameAchievement
                        achievement.percent = percentAchievement
                        achievement.dateTime = dateTime
                        achievementDao.insert(achievement)
                    }
                )
        }, BackpressureStrategy.BUFFER)
    }

    fun changeAchievement(nameGame: String, nameAchievement: Any?, idGame: Long,
                          percentAchievement: String, dateTime: Long): Flowable<Any> {
        return Flowable.create({
            achievementDao.getAchievementById(idGame)
                .subscribeOn(Schedulers.io())
                .subscribeBy { achievement ->
                    if (dateTime != 0L)
                        achievement.dateTime = dateTime
                    if (nameAchievement != null) {
                        achievement.nameAchievement = nameAchievement.toString()
                        achievement.percent = percentAchievement.substring(9).toDouble()
                    }
                    if (nameGame.isNotEmpty())
                        achievement.nameGame = nameGame
                    achievementDao.update(achievement)
                }
        }, BackpressureStrategy.BUFFER)
    }

    fun deleteAchievement(idGame: Long): Flowable<Any> {
        return Flowable.create({
            achievementDao.getAchievementById(idGame)
                .subscribeOn(Schedulers.io())
                .subscribeBy { achievement ->
                    achievementDao.delete(achievement)
                }
        }, BackpressureStrategy.BUFFER)
    }

    fun saveAchievements(path: String): Flowable<String> {
        return Flowable.create({ emitter ->
            val directory = File(path, "My Achievements")
            if (!directory.exists())
                directory.mkdirs()
            val achievementFile = File("${directory.path}/achievements.txt")
            achievementDao.getSortedAll()
                .subscribeOn(Schedulers.io())
                .subscribeBy { achievementList ->
                    if (achievementList.isNotEmpty()) {
                        try {
                            val writer = BufferedWriter(FileWriter(achievementFile))
                            for (achievement in achievementList) {
                                writer.write("${achievement.idGame};${achievement.nameGame};" +
                                        "${achievement.nameAchievement};${achievement.percent};" +
                                        "${achievement.dateTime}")
                                writer.newLine()
                            }
                            writer.close()
                            emitter.onNext("Сохранение в файл успешно завершено!")
                        } catch (e: IOException) {
                            emitter.onNext("Произошла ошибка при сохранении в файл!")
                        }
                    }
                }
        }, BackpressureStrategy.BUFFER)
    }

    fun loadAchievements(path: String): Flowable<String> {
        return Flowable.create({ emitter ->
            val directory = File(path, "My Achievements")
            val achievementFile = File("${directory.path}/achievements.txt")
            try {
                val reader = BufferedReader(FileReader(achievementFile))
                var line = reader.readLine()
                while (line != null) {
                    val idGame = line.split(";")[0].toLong()
                    val nameGame = line.split(";")[1]
                    val nameAchievement = line.split(";")[2]
                    val percent = line.split(";")[3].toDouble()
                    val dateTime = line.split(";")[4].toLong()
                    achievementDao.getAchievementById(idGame)
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                            onComplete = {
                                val achievement = Achievement()
                                achievement.idGame = idGame
                                achievement.nameGame = nameGame
                                achievement.nameAchievement = nameAchievement
                                achievement.percent = percent
                                achievement.dateTime = dateTime
                                achievementDao.insert(achievement)
                            }
                        )
                    line = reader.readLine()
                }
                reader.close()
                emitter.onNext("Данные из файла успешно загружены!")
            } catch (ex: IOException) {
                emitter.onNext("Произошла ошибка при загрузке данных из файла!")
            }
        }, BackpressureStrategy.BUFFER)
    }
}