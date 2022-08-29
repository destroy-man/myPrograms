package ru.korobeynikov.myachievements.achievement

sealed class MainIntent {
    data class GetAllAchievements(val nameGame: String) : MainIntent()
    object UpdateAllAchievements : MainIntent()
    data class SearchAchievements(val idGame: Long) : MainIntent()
    data class GetPercentForAchievement(val achievement: String) : MainIntent()
    data class AddAchievement(val nameGame: String, val nameAchievement: String, val idGame: Long,
                              val percentAchievement: Double, val dateAchievement: String,
                              val timeAchievement: String) : MainIntent()
    data class ChangeAchievement(val nameGame: String, val nameAchievement: Any?, val idGame: Long,
                                 val percentAchievement: String, val dateAchievement: String,
                                 val timeAchievement: String) : MainIntent()
    data class DeleteAchievement(val idGame: Long) : MainIntent()
    data class SaveAchievements(val path: String) : MainIntent()
    data class LoadAchievements(val path: String) : MainIntent()
}