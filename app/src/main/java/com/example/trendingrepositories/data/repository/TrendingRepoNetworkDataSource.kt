package com.example.trendingrepositories.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trendingrepositories.data.api.TrendingRepoInterface
import com.example.trendingrepositories.data.pojo.RepoDetails
import com.example.trendingrepositories.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendingRepoNetworkDataSource(
    private val apiService: TrendingRepoInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _downloadedRepoResponse = MutableLiveData<Resource<List<RepoDetails>>>()
    val downloadRepoResponse: LiveData<Resource<List<RepoDetails>>>
        get() = _downloadedRepoResponse

    fun fetchRepoDetails() {
        _downloadedRepoResponse.postValue(Resource.loading())
        try {
            compositeDisposable.add(
                apiService.getTrendingRepo("daily", "")
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedRepoResponse.postValue(Resource.success(it))

                        },
                        {
                            it.localizedMessage?.let { error ->
                                _downloadedRepoResponse.postValue(Resource.error(error))
                            }
                        }
                    )
            )

        } catch (e: Exception) {
            e.message?.let { _downloadedRepoResponse.postValue(Resource.error(it)) }
        }


    }
}