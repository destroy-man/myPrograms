package ru.korobeynikov.databasegames

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.*

class MainModel(private val dbGames: DBGames) {

    private val logTag = "myErrors" //метка для логгирования ошибок

    fun getGamesFromDB(): List<Game> {
        val listGames = ArrayList<Game>()
        val db = dbGames.writableDatabase
        val c = db.query("Games", null, null, null, null,
            null, null)
        if (c.moveToFirst()) {
            val idColIndex = c.getColumnIndex("id")
            val nameColIndex = c.getColumnIndex("name")
            val ratingColIndex = c.getColumnIndex("rating")
            val yearColIndex = c.getColumnIndex("year")
            val genreColIndex = c.getColumnIndex("genre")
            do {
                listGames.add(Game(c.getInt(idColIndex), c.getString(nameColIndex), c.getInt(ratingColIndex),
                    c.getInt(yearColIndex), c.getInt(genreColIndex)))
            } while (c.moveToNext())
        }
        c.close()
        db.close()
        return listGames
    }

    //Проверка на существование игры в базе данных
    private fun isGameExist(nameGameText: String, yearGameText: String, db: SQLiteDatabase): Boolean {
        val selection = "name=? AND year=?"
        val selectionArgs = arrayOf(nameGameText, yearGameText)
        val c = db.query("Games", null, selection, selectionArgs, null,
            null, null)
        val isExist = c.moveToFirst()
        c.close()
        return isExist
    }

    fun addGameInDB(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        val db = dbGames.writableDatabase
        val cv = ContentValues()
        if (!isGameExist(nameGameText, yearGameText, db)) {
            cv.put("name", nameGameText)
            cv.put("rating", ratingGameText)
            cv.put("year", yearGameText)
            cv.put("genre", genreId)
            db.insert("Games", null, cv)
        }
        db.close()
    }

    fun changeGameInDB(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        val db = dbGames.writableDatabase
        val cv = ContentValues()
        if (ratingGameText.isNotEmpty())
            cv.put("rating", ratingGameText)
        if (genreId > 0)
            cv.put("genre", genreId)
        if (cv.size() > 0)
            if (yearGameText.isEmpty())
                db.update("Games", cv, "name=?", arrayOf(nameGameText))
            else
                db.update("Games", cv, "name=? AND year=?",
                    arrayOf(nameGameText, yearGameText))
        db.close()
    }

    fun deleteGameInDB(nameGameText: String, yearGameText: String) {
        val db = dbGames.writableDatabase
        if (yearGameText.isEmpty())
            db.delete("Games", "name='$nameGameText'", null)
        else
            db.delete("Games", "name='$nameGameText' AND year='$yearGameText'", null)
        db.close()
    }

    fun saveGames(path: String, listGames: List<Game>?) {
        val directory = File(path, "List games")
        if (!directory.exists())
            directory.mkdirs()
        val gamesFile = File("${directory.path}/games.txt")
        try {
            if (listGames != null && listGames.isNotEmpty()) {
                val writer = BufferedWriter(FileWriter(gamesFile))
                for (game in listGames) {
                    writer.write("${game.name};${game.rating};${game.year};${game.genre}")
                    writer.newLine()
                }
                writer.close()
            }
        } catch (ex: IOException) {
            Log.d(logTag, ex.message.toString())
        }
    }

    fun loadGames(path: String) {
        val directory = File(path, "List games")
        val gamesFile = File("${directory.path}/games.txt")
        try {
            val reader = BufferedReader(FileReader(gamesFile))
            var line = reader.readLine()
            while (line != null) {
                addGameInDB(line.split(";")[0], line.split(";")[1], line.split(";")[2],
                    line.split(";")[3].toInt())
                line = reader.readLine()
            }
            reader.close()
        } catch (ex: IOException) {
            Log.d(logTag, ex.message.toString())
        }
    }
}