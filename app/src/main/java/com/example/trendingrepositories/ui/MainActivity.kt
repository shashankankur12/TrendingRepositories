package com.example.trendingrepositories.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendingrepositories.R
import com.example.trendingrepositories.data.api.TrendingRepoClient
import com.example.trendingrepositories.data.api.TrendingRepoInterface
import com.example.trendingrepositories.utils.Resource
import com.example.trendingrepositories.data.repository.TrendingRepoRepository
import com.example.trendingrepositories.utils.snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var movieRepository: TrendingRepoRepository
    private val repoAdaptor by lazy { TrendingRepoAdaptor() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        editTextSearch.addTextChangedListener(this)
        imageViewCancel.setOnClickListener(this)

        val apiService: TrendingRepoInterface = TrendingRepoClient.getClient()
        movieRepository = TrendingRepoRepository(apiService)
        viewModel = getViewModel()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.trendingRepoDetails.observe(this, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    progressBarPopular.visibility = View.GONE
                    response.data?.let { repoAdaptor.updateRepoData(it) }
                }
                Resource.Status.ERROR -> {
                    progressBarPopular.visibility = View.GONE
                    response.message?.let { main_layout.snackbar(it) }
                }
                Resource.Status.LOADING -> {
                    progressBarPopular.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setRecyclerView() {
        rvRepoList.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager
            val dividerItemDecoration =
                DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = repoAdaptor

        }
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(text: Editable?) {
        text?.let {
            repoAdaptor.setSearchEnabled(text.isNotEmpty(), text.toString())
            imageViewCancel.visibility = if (text.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                imageViewCancel.id -> {
                    editTextSearch.setText("")
                }
            }
        }
    }
}