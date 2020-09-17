package com.example.movieapp.movieapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.movieapp.adapter.AutoCompleteRecyclerAdapter
import com.example.movieapp.movieapp.model.SearchResultData
import com.example.movieapp.movieapp.viewmodel.AutoCompleteViewModel
import com.example.movieapp.movieapp.viewmodel.MovieViewModelFactory
import com.example.moviesapp.movieapp.R
import java.lang.IndexOutOfBoundsException

class AutoCompleteActivity : AppCompatActivity() {

    private lateinit var autoCompleteActivityViewModel : AutoCompleteViewModel
    private lateinit var recyclerAdapter: AutoCompleteRecyclerAdapter
    private val recyclerView : RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }
    private val searchEditText : EditText by lazy {
        findViewById<EditText>(R.id.editText)
    }

    companion object {
        const val NO_SEARCH_RESULT_ID = "1234abc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auto_complete_activity)
        autoCompleteActivityViewModel = ViewModelProviders.of(this,
            MovieViewModelFactory()
        ).get(AutoCompleteViewModel::class.java)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0 != null) {
                    autoCompleteActivityViewModel.updateQueryString(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        searchEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                sendSearchResult()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        recyclerAdapter =
            AutoCompleteRecyclerAdapter(
                this,
                ArrayList()
            ) {
                sendSearchResult()
            }
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = recyclerAdapter
        autoCompleteActivityViewModel.getAutoCompleteLiveData().observe(this, Observer{
            if (it is SearchResultData) {
                recyclerAdapter.listOfMovies.clear()
                recyclerAdapter.listOfMovies.addAll(it.Search)
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun sendSearchResult() {
        try {
            if (!TextUtils.isEmpty(searchEditText.text.toString()) && !(recyclerView.adapter as AutoCompleteRecyclerAdapter).listOfMovies[0].equals(
                    NO_SEARCH_RESULT_ID
                )) {
                setResult(MainActivity.SEARCH_REQUEST_SUCCESS, Intent().apply {
                    putExtra("key",searchEditText.text.toString())
                })
                finish()
            }
        }
        catch (indexOutOfBounds : IndexOutOfBoundsException) {
            indexOutOfBounds.printStackTrace()
        }

    }

    override fun onBackPressed() {
        setResult(MainActivity.SEARCH_REQUEST_FAILURE)
        super.onBackPressed()
    }
}