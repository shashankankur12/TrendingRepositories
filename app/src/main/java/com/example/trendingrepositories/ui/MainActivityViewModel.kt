package com.example.trendingrepositories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.trendingrepositories.data.pojo.RepoDetails
import com.example.trendingrepositories.utils.Resource
import com.example.trendingrepositories.data.repository.TrendingRepoRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val trendingRepoRepository: TrendingRepoRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val trendingRepoDetails: LiveData<Resource<List<RepoDetails>>> by lazy {
        trendingRepoRepository.fetchRepoDetails(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}