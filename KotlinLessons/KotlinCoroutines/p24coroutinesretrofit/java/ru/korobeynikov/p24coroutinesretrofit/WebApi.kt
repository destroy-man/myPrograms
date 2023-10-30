package ru.korobeynikov.p24coroutinesretrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WebApi {
    @GET("messages{page}.json")
    suspend fun messages(@Path("page") page: Int): Response<List<Message>>
}