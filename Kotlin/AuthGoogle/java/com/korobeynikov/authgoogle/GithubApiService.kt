package com.korobeynikov.authgoogle

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    //Поиск в GitHub
    @GET("search/users")
    fun search(@Query("q") query: String, //запрос
               @Query("page") page: Int, //с какой страницы поиска брать результаты
               @Query("per_page") perPage: Int): Observable<Result> //количество получаемых результатов на страницу

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): GithubApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()

            return retrofit.create(GithubApiService::class.java);
        }
    }
}