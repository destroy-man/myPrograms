package ru.korobeynikov.myachievements.server

import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

//Интерфейс для взаимодействия с сервером
interface AchievementApi {
    @GET("v0002/?")
    fun getListAchievements(@Query("gameid") id: Long): Flowable<ListAchievements>
}