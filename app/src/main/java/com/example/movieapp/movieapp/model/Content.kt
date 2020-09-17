package com.example.movieapp.movieapp.model

import com.example.movieapp.movieapp.db.MovieInfo

sealed class Content {

    abstract fun getContentType() : ContentType
}

class SearchResultData : Content() {

    var pageNo: Int = 1
    var Search : List<MovieInfo> = ArrayList()

    override fun getContentType(): ContentType {
        return ContentType.SEARCH_RESULT_CONTENT
    }

}
enum class ContentType {

    SEARCH_RESULT_CONTENT
}
