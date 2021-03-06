package com.korobeynikov.authgoogle

import io.reactivex.Observable

class SearchRepository(val apiService: GithubApiService) {
    //Конструктор поиска
    fun searchUsers(nameGithubUser: String,numPage: Int): Observable<Result> {
        return apiService.search(query = "$nameGithubUser in:login",numPage,30)
    }
}