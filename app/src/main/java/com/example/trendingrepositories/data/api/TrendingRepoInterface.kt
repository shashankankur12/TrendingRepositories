package com.example.trendingrepositories.data.api


import com.example.trendingrepositories.data.pojo.RepoDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingRepoInterface {
    @GET("/repositories")
    fun getTrendingRepo(@Query("since") since: String, @Query("language") language: String): Single<List<RepoDetails>>

}