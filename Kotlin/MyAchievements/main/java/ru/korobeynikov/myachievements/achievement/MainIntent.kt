package ru.korobeynikov.myachievements.achievement

sealed class MainIntent {
    data class getAllAchievements(val nameGame: String) : MainIntent()
    object updateAllAchievements : MainIntent()
    data class searchAchievements(val idGame: Long) : MainIntent()
    data class getPercentForAchievement(val achievement: String) : MainIntent()
    data class addAchievement(val nameGame: String, val nameAchievement: String, val idGame: Long,
        val percentAchievement: Double, val dateAchievement: String, val timeAchievement: String, ) : MainIntent()
    data class changeAchievement(val nameGame: String, val nameAchievement: Any?, val idGame: Long,
        val percentAchievement: String, val dateAchievement: String, val timeAchievement: String, ) : MainIntent()
    data class deleteAchievement(val idGame: Long) : MainIntent()
    data class saveAchievements(val path: String) : MainIntent()
    data class loadAchievements(val path: String) : MainIntent()
}
