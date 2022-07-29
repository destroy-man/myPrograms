package ru.korobeynikov.myachievements.achievement

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.korobeynikov.myachievements.database.Achievement
import java.text.SimpleDateFormat

class MainPresenter(private val mainRepository: MainRepository,
    private var mainState: MainState, val states: BehaviorSubject<MainState>) {

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
                states.onNext(mainState)
            }
    }

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.getAllAchievements -> {
                getListAchievements(mainState, intent.nameGame, null)
            }
            is MainIntent.updateAllAchievements -> {
                mainRepository.updateAllAchievements()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy {
                        getListAchievements(mainState, "", null)
                    }
            }
            is MainIntent.searchAchievements -> {
                mainRepository.searchAchievements(intent.idGame)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { mapAchievements ->
                        mainState = mainState.copy(achievementsFromGame = mapAchievements)
                        getListAchievements(mainState, "", LoadingObject.achievements)
                    }
            }
            is MainIntent.getPercentForAchievement -> {
                val percent = mainState.achievementsFromGame[intent.achievement].toString()
                mainState = mainState.copy(achievementPercent = percent)
                getListAchievements(mainState, "", LoadingObject.percent)
            }
            is MainIntent.addAchievement -> {
                mainRepository.addAchievement(intent.nameGame, intent.nameAchievement, intent.idGame,
                    intent.percentAchievement, getDateTime(intent.dateAchievement, intent.timeAchievement))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        mainState = mainState.copy(message = message)
                        getListAchievements(mainState, "", LoadingObject.message)
                    }
            }
            is MainIntent.changeAchievement -> {
                mainRepository.changeAchievement(intent.nameGame, intent.nameAchievement, intent.idGame,
                    intent.percentAchievement, getDateTime(intent.dateAchievement, intent.timeAchievement))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        mainState = mainState.copy(message = message)
                        getListAchievements(mainState, "", LoadingObject.message)
                    }
            }
            is MainIntent.deleteAchievement -> {
                mainRepository.deleteAchievement(intent.idGame)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        mainState = mainState.copy(message = message)
                        getListAchievements(mainState, "", LoadingObject.message)
                    }
            }
            is MainIntent.saveAchievements -> {
                mainRepository.saveAchievements(intent.path)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { message ->
                        mainState = mainState.copy(message = message)
                        getListAchievements(mainState, "", LoadingObject.message)
                    }
            }
            is MainIntent.loadAchievements -> {
                mainRepository.loadAchievements(intent.path)
                    .observeOn(AndroidSchedulers.mainThread()).take(1)
                    .subscribeBy { message ->
                        getListAchievements(mainState, "", null)
                    }
            }
        }
    }
}

data class MainState(
    val loading: LoadingObject? = null,
    val achievementsFromDB: List<Achievement> = emptyList(),
    val achievementsFromGame: Map<String, String> = emptyMap(),
    val listChange: List<String> = emptyList(),
    val achievementPercent: String = "",
    val message: String = "",
)

enum class LoadingObject {
    achievements, percent, message
}