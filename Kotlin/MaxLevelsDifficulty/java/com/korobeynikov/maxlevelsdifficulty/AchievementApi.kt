package com.korobeynikov.maxlevelsdifficulty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AchievementApi {
    @GET("v0002/?")
    fun getListAchievements(@Query("gameid") id:Long): Call<ListAchievements>
}