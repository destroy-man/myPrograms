package ru.korobeynikov.databasegames

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel(private val mainModel: MainModel, private val scope: CoroutineScope) : ViewModel() {

    private lateinit var listGames: List<Game> //список всех игр
    var listDisplayedGames: ObservableList<Game> = ObservableArrayList() //список игр для отображения
    val messageLiveData = MutableLiveData<String>() //LiveData хранящая сообщения

    //Изменение списка игр для отображения
    private fun loadData(displayedGames: List<Game>) {
        listDisplayedGames.clear()
        listDisplayedGames.addAll(displayedGames)
    }

    fun getGames() {
        listGames = mainModel.getGamesFromDB()
        loadData(listGames)
    }

    fun filterGames(nameGameText: String, ratingGameText: String, yearGameText: String,
                    genreGame: Int, isSort: Boolean) {
        var filterListGames = listGames
        if (nameGameText.isNotEmpty())
            filterListGames = filterListGames.filter { it.name.contains(nameGameText, true) }
        if (ratingGameText.isNotEmpty())
            filterListGames = filterListGames.filter { it.rating == ratingGameText.toInt() }
        if (yearGameText.isNotEmpty())
            filterListGames = filterListGames.filter { it.year.toString().contains(yearGameText) }
        if (genreGame > 0)
            filterListGames = filterListGames.filter { it.genre == genreGame }
        if (isSort)
            filterListGames = filterListGames.sortedWith(compareByDescending(Game::rating)
                .thenBy(Game::genre).thenByDescending(Game::year).thenByDescending(Game::id))
        loadData(filterListGames)
    }

    fun addGame(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        scope.launch {
            mainModel.addGameInDB(nameGameText, ratingGameText, yearGameText, genreId)
            getGames()
        }
    }

    fun changeGame(nameGameText: String, ratingGameText: String, yearGameText: String, genreId: Int) {
        scope.launch {
            mainModel.changeGameInDB(nameGameText, ratingGameText, yearGameText, genreId)
            getGames()
        }
    }

    fun deleteGame(nameGameText: String, yearGameText: String) {
        scope.launch {
            mainModel.deleteGameInDB(nameGameText, yearGameText)
            getGames()
        }
    }

    fun saveGamesInFile(path: String) {
        scope.launch {
            messageLiveData.postValue(mainModel.saveGames(path, listGames))
        }
    }

    fun loadGamesFromFile(path: String) {
        scope.launch {
            messageLiveData.postValue(mainModel.loadGames(path))
            getGames()
        }
    }
}