package com.noureldin.cinemaxplay.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.noureldin.cinemaxplay.Adapters.ActorsListAdapter
import com.noureldin.cinemaxplay.Adapters.CategoryEachFilmListAdapter
import com.noureldin.cinemaxplay.Domain.FilmItem
import com.noureldin.cinemaxplay.R

class DetailsActivity : AppCompatActivity() {
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var progressBar: ProgressBar
    private lateinit var titleTxt: TextView
    private lateinit var MovieRateTxT: TextView
    private lateinit var MovieDateTxT: TextView
    private lateinit var MovieSummaryInfo: TextView
    private lateinit var movieActorInfo: TextView
    private var idFilm: Int = 0
    private lateinit var imageViewDetails: ImageView
    private lateinit var backImg: ImageView
    private lateinit var adapterActorList: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        idFilm = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this)
        progressBar.visibility = View.VISIBLE
        scrollView.visibility = View.GONE

        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm,
            Response.Listener<String> { response ->
                val gson = Gson()
                progressBar.visibility = View.INVISIBLE
                scrollView.visibility = View.VISIBLE

                val FilmItem = gson.fromJson(response, FilmItem::class.java)
                Glide.with(this@DetailsActivity)
                    .load(FilmItem.poster)
                    .into(imageViewDetails)
                titleTxt.text = FilmItem.title
                MovieRateTxT.text = FilmItem.imdbRating
                MovieDateTxT.text = FilmItem.runtime
                MovieSummaryInfo.text = FilmItem.plot
                movieActorInfo.text = FilmItem.actors
                if (FilmItem.images != null) {
                    adapterActorList = ActorsListAdapter(FilmItem.images)
                    recyclerViewActors.adapter = adapterActorList
                }
                if (FilmItem.genres != null) {
                    adapterCategory = CategoryEachFilmListAdapter(FilmItem.genres)
                    recyclerViewCategory.adapter = adapterCategory
                }
            },
            { error -> progressBar.visibility = View.VISIBLE })
        mRequestQueue.add(mStringRequest)
    }

    private fun initView() {
        titleTxt = findViewById(R.id.MovieNameTxT)
        progressBar = findViewById(R.id.progressBarDetails)
        scrollView = findViewById(R.id.scrollView2)
        imageViewDetails = findViewById(R.id.imageViewDetails)
        MovieRateTxT = findViewById(R.id.MovieRateTxT)
        MovieDateTxT = findViewById(R.id.MovieDateTxT)
        MovieSummaryInfo = findViewById(R.id.MoviesSummary)
        movieActorInfo = findViewById(R.id.movieActorInfo)
        backImg = findViewById(R.id.back)
        recyclerViewCategory = findViewById(R.id.genreView)
        recyclerViewActors = findViewById(R.id.imagesRecycler)
        recyclerViewActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        backImg.setOnClickListener { finish() }
    }
}