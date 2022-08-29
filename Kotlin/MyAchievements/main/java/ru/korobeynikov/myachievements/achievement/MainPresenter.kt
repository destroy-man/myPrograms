package ru.korobeynikov.myachievements.achievement

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.text.SimpleDateFormat

class MainPresenter(private val mainRepository: MainRepository, private var mainState: MainState) {

    private var view: MainActivity? = null //объект View

    //присоединить объект View
    fun attachView(mainActivity: MainActivity) {
        view = mainActivity
    }

    //отсоединить объект View
    fun detachView() {
        view = null
    }

    //Приведение даты и времени к нужному формату
    private fun getDateTime(date: String, time: String): Long {
        var dateTime = 0L
        if (date.isNotEmpty() && time.isNotEmpty()) {
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            dateTime = formatter.parse("$date $time").time
        }
        return dateTime
    }

    private fun getListAchievements(state: MainState, nameGame: String, loading: LoadingObject?) {
        mainRepository.getListAchievements(nameGame)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { listAchievements ->
                mainState = state.copy(loading = loading, achievementsFromDB = listAchievements)
                view?.render(mainState)
            }
    }

    //Обработка всех действий пользователя
    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.GetAllAchievements -> getListAchievements(mainState, intent.nameGame, null)
            is MainIntent.UpdateAllAchievements ->
                mainRepository.updateAllAchievements()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getListAchievements(mainState, "", null)
                    }
            is MainIntent.SearchAchievements ->
                mainRepository.searchAchievements(intent.idGame)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { mapAchievements ->
                        mainState = mainState.copy(achievementsFromGame = mapAchievements)
                        getListAchievements(mainState, "", LoadingObject.Achievements)
                    }
            is MainIntent.GetPercentForAchievement -> {
                val percent = mainState.achievementsFromGame[intent.achievement].toString()
                mainState = mainState.copy(achievementPercent = percent)
                getListAchievements(mainState, "", LoadingObject.Percent)
            }
            is MainIntent.AddAchievement ->
                mainRepository.addAchievement(intent.nameGame, intent.nameAchievement,
                    intent.idGame, intent.percentAchievement, getDateTime(intent.dateAchievement,
                    intent.timeAchievement))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getListAchievements(mainState, "", null)
                    }
            is MainIntent.ChangeAchievement ->
                mainRepository.changeAchievement(intent.nameGame, intent.nameAchievement,
                    intent.idGame, intent.percentAchievement, getDateTime(intent.dateAchievement,
                    intent.timeAchievement))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getListAchievements(mainState, "", null)
                    }
            is MainIntent.DeleteAchievement ->
                mainRepository.deleteAchievement(intent.idGame)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getListAchievements(mainState, "", null)
                    }
            is MainIntent.SaveAchievements ->
                mainRepository.saveAchievements(intent.path)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        getListAchievements(mainState, "", null)
                        view?.showMessage(message)
                    }
            is MainIntent.LoadAchievements ->
                mainRepository.loadAchievements(intent.path)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        getListAchievements(mainState, "", null)
                        view?.showMessage(message)
                    }
        }
    }
}