package com.example.trendingrepositories.data.repository

import androidx.lifecycle.LiveData
import com.example.trendingrepositories.data.api.TrendingRepoInterface
import com.example.trendingrepositories.data.pojo.RepoDetails
import com.example.trendingrepositories.utils.Resource
import io.reactivex.disposables.CompositeDisposable


class TrendingRepoRepository (private val apiService : TrendingRepoInterface) {

    lateinit var trendingRepoDataSource: TrendingRepoNetworkDataSource

    fun fetchRepoDetails (compositeDisposable: CompositeDisposable,) : LiveData<Resource<List<RepoDetails>>> {

        trendingRepoDataSource = TrendingRepoNetworkDataSource(apiService,compositeDisposable)
        trendingRepoDataSource.fetchRepoDetails()

        return trendingRepoDataSource.downloadRepoResponse

    }


}