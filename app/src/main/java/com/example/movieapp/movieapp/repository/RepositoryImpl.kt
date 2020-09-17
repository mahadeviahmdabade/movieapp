package com.example.movieapp.movieapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.movieapp.AutoCompleteRepository
import com.example.movieapp.movieapp.view.MainActivityRepository
import com.example.movieapp.movieapp.db.AppDatabase
import com.example.movieapp.movieapp.db.MovieInfo
import com.example.movieapp.movieapp.model.Content
import com.example.movieapp.movieapp.model.SearchResultData
import com.example.movieapp.movieapp.view.AutoCompleteActivity
import com.example.movieapp.movieapp.webservice.WebService
import com.google.gson.Gson
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val retrofit: Retrofit?, val appDatabase: AppDatabase?) : AutoCompleteRepository,
    MainActivityRepository {

    private var webService = retrofit?.create(WebService::class.java)

    private val autoCompletePublishSubject = PublishRelay.create<String>()

    private var autoCompleteContent : MutableLiveData<Content> = MutableLiveData()

    val executorService = Executors.newSingleThreadExecutor()

    init {
        autoCompletePublishSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {query ->
                return@switchMap Single.create<SearchResultData> {

                    val listOfMovies = appDatabase?.getMovieInfoDao()?.getMoviesInfo(query)
                    if (query.length==0) {
                        it.onSuccess(SearchResultData().apply { Search = ArrayList<MovieInfo>() })
                    }
                    else if (listOfMovies !=null && !listOfMovies.isEmpty() && query.length>0) {

                        it.onSuccess(SearchResultData().apply { Search = listOfMovies })
                        return@create
                    }
                    webService?.getSearchResult(query)?.enqueue(object : Callback<ResponseBody>{
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                        }

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>) {

                            val searchResultData  = Gson().fromJson(response.body()?.string(),
                                SearchResultData::class.java)
                            if (searchResultData == null || searchResultData.Search.isEmpty()) {
                                if (!it.isDisposed)
                                it.onError(Throwable("no data found"))
                            }
                            else {

                                if (searchResultData.Search!= null) {
                                    executorService.submit {
                                        appDatabase?.getMovieInfoDao()
                                           ?.insert(searchResultData.Search!!)
                                    }
                                }

                                it.onSuccess(searchResultData)
                            }
                        }

                    })
                }.onErrorReturn {
                    return@onErrorReturn SearchResultData()
                        .apply { Search = ArrayList<MovieInfo>().apply { add(
                        MovieInfo(
                            "No result found", "",
                            AutoCompleteActivity.NO_SEARCH_RESULT_ID, ""
                        )
                    ) } }
                }.toObservable()

            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                autoCompleteContent.value = result
            },{ t: Throwable? -> Log.v(t.toString(), "Failed to get search results")
                autoCompleteContent.value = SearchResultData()
                    .apply { Search = ArrayList<MovieInfo>().apply { add(
                    MovieInfo(
                        "No result found", "",
                        AutoCompleteActivity.NO_SEARCH_RESULT_ID, ""
                    )
                ) } }
            })
    }

    override fun getAutoCompleteLiveData(): LiveData<Content> {
        return autoCompleteContent
    }

    override fun updateQueryString(query: String) {
        autoCompletePublishSubject.accept(query)
    }

    override fun getMoviesListLiveData(): LiveData<Content> {
        return autoCompleteContent
    }

    override fun searchMovies(title: String, pageNo: Int) {
        webService?.getSearchResult(title,pageNo = pageNo)?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val searchResultData  = Gson().fromJson(response.body()?.string(),
                    SearchResultData::class.java)
                if (!(searchResultData == null || searchResultData.Search.isEmpty())) {
                    searchResultData.pageNo = pageNo
                    autoCompleteContent.value = searchResultData
                }
            }

        })
    }

}