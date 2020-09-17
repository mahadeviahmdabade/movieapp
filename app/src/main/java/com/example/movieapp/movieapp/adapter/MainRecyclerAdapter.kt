package com.example.movieapp.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.movieapp.db.MovieInfo
import com.example.moviesapp.movieapp.R
import com.squareup.picasso.Picasso

class MainRecyclerAdapter(context : Context, val listOfMovies : ArrayList<MovieInfo>) : RecyclerView.Adapter<MainRecyclerAdapterViewHolder>() {

    var pageNo: Int = 1
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclerAdapterViewHolder {
        val view = layoutInflater.inflate(R.layout.main_recycler_item,parent,false)
        return MainRecyclerAdapterViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: MainRecyclerAdapterViewHolder, position: Int) {

        Picasso.get().load(listOfMovies[position].Poster).fit().centerCrop().into(holder.imageView)
        holder.title.text = listOfMovies[position].Title
        holder.year.text = listOfMovies[position].Year
    }

}

class MainRecyclerAdapterViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    val imageView = itemView.findViewById<ImageView>(R.id.imgView)
    val title = itemView.findViewById<TextView>(R.id.title)
    val year = itemView.findViewById<TextView>(R.id.year)
}