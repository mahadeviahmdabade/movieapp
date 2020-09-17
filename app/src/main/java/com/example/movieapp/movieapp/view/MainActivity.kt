package com.example.movieapp.movieapp.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.movieapp.adapter.MainRecyclerAdapter
import com.example.movieapp.movieapp.model.SearchResultData
import com.example.movieapp.movieapp.util.DatabaseAccessor
import com.example.movieapp.movieapp.viewmodel.MainActivityViewModel
import com.example.movieapp.movieapp.viewmodel.MovieViewModelFactory
import com.example.moviesapp.movieapp.R


class MainActivity : AppCompatActivity() {

    private lateinit var key: String
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private var isLoading = false
    private var isExpectingResult = false
    private val recyclerView : RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.main_recycler_view)
    }

    companion object {
        val SEARCH_REQUEST = 1000
        val SEARCH_REQUEST_SUCCESS = 1001
        val SEARCH_REQUEST_FAILURE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseAccessor.initializedb(applicationContext)
        mainActivityViewModel = ViewModelProviders.of(this,
            MovieViewModelFactory()
        ).get(MainActivityViewModel::class.java)
        if (savedInstanceState != null) {
            isExpectingResult = savedInstanceState.getBoolean("expectingValue", false)
            key = savedInstanceState.getString("key","avengers")
        }
        initData()
    }

    private fun initData() {
        if (checkIfInternetConnected()) {
            setContentView(R.layout.activity_main)
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)
            mainActivityViewModel.getMoviesListLiveData().observe(this, Observer{
                if (it is SearchResultData) {
                    isLoading = false
                    val mainRecyclerAdapter = recyclerView.adapter as MainRecyclerAdapter?
                    mainRecyclerAdapter?.listOfMovies?.addAll((it.Search))
                    mainRecyclerAdapter?.pageNo = it.pageNo
                    mainRecyclerAdapter?.notifyDataSetChanged()
                }
            })
            findViewById<TextView>(R.id.textView).setOnClickListener {
                isExpectingResult = true
                startActivityForResult(Intent(this@MainActivity,
                    AutoCompleteActivity::class.java),
                    SEARCH_REQUEST
                )
            }
            recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager
                    val visibleItemCount = layoutManager?.getChildCount()
                    val totalItemCount = layoutManager?.getItemCount()
                    val firstVisibleItemPosition =
                        (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                    if (!isLoading()) {
                        if (visibleItemCount!! + firstVisibleItemPosition >= totalItemCount!! && firstVisibleItemPosition >= 0) {
                            isLoading = true
                            mainActivityViewModel.searchMovies(key,(recyclerView.adapter as MainRecyclerAdapter).pageNo+1)
                        }
                    }
                }

                fun isLoading() : Boolean {
                    return isLoading
                }
            })
            if (!isExpectingResult) {
                if (::key.isInitialized) {
                    searchMovie(key)
                }
                else {
                    searchMovie("avengers")
                }
            }
        }
        else {
            setContentView(R.layout.refresh_internet)
            findViewById<ImageView>(R.id.refresh).setOnClickListener {
                initData()
            }
        }
    }

    private fun searchMovie(key: String?) {
        if (key != null) {
            val mainRecyclerAdapter =
                MainRecyclerAdapter(
                    this@MainActivity,
                    ArrayList()
                )
            this.key = key
            recyclerView.adapter = mainRecyclerAdapter
            mainActivityViewModel.searchMovies(key, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == SEARCH_REQUEST_SUCCESS) {
            isExpectingResult = false
            if (checkIfInternetConnected()) {
                searchMovie(data?.getStringExtra("key"))
            }
        }
        else if (resultCode == SEARCH_REQUEST_FAILURE) {
            isExpectingResult = false
            if (checkIfInternetConnected()) {
                searchMovie(key)
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("expectingValue",isExpectingResult)
        if (::key.isInitialized) {
            outState.putString("key", key)
        }
        super.onSaveInstanceState(outState)
    }

    private fun checkIfInternetConnected() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return (activeNetworkInfo != null && activeNetworkInfo.isConnected())
    }
}
