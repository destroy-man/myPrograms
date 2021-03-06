package com.korobeynikov.authgoogle

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        val apiService = GithubApiService.create()
        return SearchRepository(apiService)
    }
}