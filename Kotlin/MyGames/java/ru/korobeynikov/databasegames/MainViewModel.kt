package ru.korobeynikov.databasegames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val mainModel: MainModel, val listGames: MutableLiveData<List<String>>) : ViewModel() {

    lateinit var message: String

    fun getGames(nameGameText: String, ratingGameText: String, yearGameText: String, genreGame: Int, isSort: Boolean) {
        var selection = StringBuilder()
        val selectionArgs = ArrayList<String>()
        if (nameGameText.isNotEmpty()) {
            selection.append("name LIKE ? AND ")
            selectionArgs.add("%$nameGameText%")
        }
        if (ratingGameText.isNotEmpty()) {
            selection.append("rating=? AND ")
            selectionArgs.add(ratingGameText)
        }
        if (yearGameText.isNotEmpty()) {
            selection.append("year LIKE ? AND ")
            selectionArgs.add("%$yearGameText%")
        }
        if (genreGame > 0) {
            selection.append("genre=?")
            selectionArgs.add(genreGame.toString())
        }
        if (selection.toString().isNotEmpty() && selection.toString().endsWith(" AND "))
            selection = StringBuilder(selection.substring(0, selection.length - 5))
        var order: String? = null
        if (isSort)
            order = "rating desc,genre asc,year desc,id desc"
        listGames.value = mainModel.getGamesFromDB(selection.toString(), selectionArgs.toTypedArray(), order)
    }

    fun getGames() {
        listGames.value = mainModel.getGamesFromDB(null, null, null)
    }

    fun addGame(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        message = mainModel.addGameInDB(nameGameText, ratingGameText, yearGameText, genreId)
        getGames()
    }

    fun changeGame(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        message = mainModel.changeGameInDB(nameGameText, ratingGameText, yearGameText, genreId)
        getGames()
    }

    fun deleteGame(nameGameText: String, yearGameText: String) {
        message = mainModel.deleteGameInDB(nameGameText, yearGameText)
        getGames()
    }

    fun saveGamesInFile(path: String) {
        message = mainModel.saveGames(path)
        getGames()
    }

    fun loadGamesFromFile(path: String) {
        message = mainModel.loadGames(path)
        getGames()
    }
}