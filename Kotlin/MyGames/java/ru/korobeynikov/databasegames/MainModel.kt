package ru.korobeynikov.databasegames

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.io.*

class MainModel(private val dbGames: DBGames) {

    fun getGamesFromDB(selection: String?, selectionArgs: Array<String>?, order: String?): List<String> {
        val listGames = ArrayList<String>()
        val db = dbGames.writableDatabase
        val c = db.query("Games", null, selection, selectionArgs, null, null, order)
        if (c.moveToFirst()) {
            val nameColIndex = c.getColumnIndex("name")
            val ratingColIndex = c.getColumnIndex("rating")
            val yearColIndex = c.getColumnIndex("year")
            val genreColIndex = c.getColumnIndex("genre")
            do {
                listGames.add("${c.getString(nameColIndex)};${c.getInt(ratingColIndex)}" +
                        ";${c.getInt(yearColIndex)};${c.getInt(genreColIndex)}")
            } while (c.moveToNext())
        }
        c.close()
        db.close()
        return listGames
    }

    private fun isGameExist(nameGameText: String, yearGameText: String, db: SQLiteDatabase): Boolean {
        val selection = "name=? AND year=?"
        val selectionArgs = arrayOf(nameGameText, yearGameText)
        val c = db.query("Games", null, selection, selectionArgs, null, null, null)
        val isExist = c.moveToFirst()
        c.close()
        return isExist
    }

    fun addGameInDB(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int): String {
        var message = ""
        val db = dbGames.writableDatabase
        val cv = ContentValues()
        message = if (!isGameExist(nameGameText, yearGameText, db)) {
            cv.put("name", nameGameText)
            cv.put("rating", ratingGameText)
            cv.put("year", yearGameText)
            cv.put("genre", genreId)
            db.insert("Games", null, cv)
            "Игра $nameGameText успешно добавлена!"
        } else
            "Данная игра уже добавлена!"
        db.close()
        return message
    }

    fun changeGameInDB(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int): String {
        val db = dbGames.writableDatabase
        val cv = ContentValues()
        cv.put("rating", ratingGameText)
        if (genreId > 0)
            cv.put("genre", genreId)
        val updCount =
            if (yearGameText.isEmpty())
                db.update("Games", cv, "name=?", arrayOf(nameGameText))
            else
                db.update("Games", cv, "name=? AND year=?", arrayOf(nameGameText, yearGameText))
        db.close()
        return when {
            updCount > 1 -> "Данные об играх успешно изменены!"
            updCount > 0 -> "Данные об игре успешно изменены!"
            else -> "По запросу ничего не найдено!"
        }
    }

    fun deleteGameInDB(nameGameText: String, yearGameText: String): String {
        val db = dbGames.writableDatabase
        val delCount =
            if (yearGameText.isEmpty())
                db.delete("Games", "name='$nameGameText'", null)
            else
                db.delete("Games", "name='$nameGameText' AND year='$yearGameText'", null)
        db.close()
        return when {
            delCount > 1 -> "Игры успешно удалены!"
            delCount > 0 -> "Игра успешно удалена!"
            else -> "По запросу ничего не найдено!"
        }
    }

    fun saveGames(path: String): String {
        val directory = File(path, "List games")
        if (!directory.exists())
            directory.mkdirs()
        val gamesFile = File("${directory.path}/games.txt")
        return try {
            val listGames = getGamesFromDB(null, null, null)
            if (listGames.isNotEmpty()) {
                val writer = BufferedWriter(FileWriter(gamesFile))
                for (game in listGames) {
                    writer.write(game)
                    writer.newLine()
                }
                writer.close()
                "Cохранение в файл успешно завершено!"
            } else
                "Нету игр для сохранения!"
        } catch (ex: IOException) {
            "Произошла ошибка при сохранении в файл!"
        }
    }

    fun loadGames(path: String): String {
        val directory = File(path, "List games")
        val gamesFile = File("${directory.path}/games.txt")
        return try {
            var haveGames = false
            val reader = BufferedReader(FileReader(gamesFile))
            var line = reader.readLine()
            while (line != null) {
                addGameInDB(line.split(";")[0], line.split(";")[1]
                    , line.split(";")[2], line.split(";")[3].toInt())
                line = reader.readLine()
                if (!haveGames)
                    haveGames = true
            }
            reader.close()
            if (haveGames)
                "Игры из файла успешно загружены!"
            else
                "В файле не обнаружено игр для загрузки!"
        } catch (ex: IOException) {
            "Произошла ошибка при загрузке из файла!"
        }
    }
}