package com.noureldin.cinemaxplay.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.noureldin.cinemaxplay.Activities.DetailsActivity
import com.noureldin.cinemaxplay.Domain.ListFilm
import com.noureldin.cinemaxplay.R

class FilmListAdapter(var items: ListFilm) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.film_viewholder, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items.data!![position].title
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(30))
        Glide.with(context!!)
            .load(items.data!![position].poster)
            .apply(requestOptions)
            .into(holder.pic)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("id", items.data!![position].id)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.data!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView
        var pic: ImageView

        init {
            titleTxt = itemView.findViewById(R.id.titleTxt)
            pic = itemView.findViewById(R.id.pic)
        }
    }
}