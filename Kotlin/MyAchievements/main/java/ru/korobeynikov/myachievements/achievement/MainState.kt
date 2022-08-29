package ru.korobeynikov.myachievements.achievement

import ru.korobeynikov.myachievements.database.Achievement

data class MainState(
    val loading: LoadingObject? = null,
    val achievementsFromDB: List<Achievement> = emptyList(),
    val achievementsFromGame: Map<String, String> = emptyMap(),
    val achievementPercent: String = ""
)