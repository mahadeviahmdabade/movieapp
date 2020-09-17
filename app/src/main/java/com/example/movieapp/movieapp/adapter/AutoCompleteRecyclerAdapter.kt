package com.example.movieapp.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.movieapp.db.MovieInfo
import com.example.moviesapp.movieapp.R

class AutoCompleteRecyclerAdapter(val context: Context, val listOfMovies : ArrayList<MovieInfo>, val onItemClick : (movieinfo : MovieInfo) -> Unit ) : RecyclerView.Adapter<AutoCompleteRecyclerViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoCompleteRecyclerViewHolder {
        val view = layoutInflater.inflate(R.layout.auto_complete_item,parent,false)
        return AutoCompleteRecyclerViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(holder: AutoCompleteRecyclerViewHolder, position: Int) {
        holder.textView.text = listOfMovies[position].Title
        holder.textView.setOnClickListener {
            onItemClick(listOfMovies[position])
        }
    }

}
class AutoCompleteRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView = view.findViewById<TextView>(R.id.txtView)
}