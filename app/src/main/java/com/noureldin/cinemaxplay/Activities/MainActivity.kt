package com.noureldin.cinemaxplay.Activities


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.noureldin.cinemaxplay.Adapters.CategoryListAdapter
import com.noureldin.cinemaxplay.Adapters.FilmListAdapter
import com.noureldin.cinemaxplay.Adapters.SliderAdapter
import com.noureldin.cinemaxplay.Domain.GenresItem
import com.noureldin.cinemaxplay.Domain.ListFilm
import com.noureldin.cinemaxplay.Domain.SliderItem
import com.noureldin.cinemaxplay.R
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var adapterBestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterLatestMovies: RecyclerView.Adapter<*>
    private lateinit var adapterCategory: RecyclerView.Adapter<*>
    private lateinit var recyclerViewBestMovies: RecyclerView
    private lateinit var recyclerViewLatestMovies: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mStringRequest: StringRequest
    private lateinit var mStringRequest2: StringRequest
    private lateinit var mStringRequest3: StringRequest
    private lateinit var loading1: ProgressBar
    private lateinit var loading2: ProgressBar
    private lateinit var loading3: ProgressBar
    private lateinit var viewPager2: ViewPager2
    private val slideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        banners()
        sendRequestBestMovies()
        sendRequestLatestMovies()
        sendRequestCategory()
    }

    private fun sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading1.visibility = View.VISIBLE
        mStringRequest = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
            { response ->
                val gson = Gson()
                loading1.visibility = View.GONE
                val item: ListFilm = gson.fromJson(response, ListFilm::class.java)
                adapterBestMovies = FilmListAdapter(item)
                recyclerViewBestMovies.adapter = adapterBestMovies
            },
            { error ->
                loading1.visibility = View.GONE
                Log.i("UiLovers", "OnErrorResponse:" + error.toString())
            })
        mRequestQueue.add(mStringRequest)
    }
    private fun sendRequestLatestMovies() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading3.visibility = View.VISIBLE
        mStringRequest3 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2",
            { response ->
                val gson = Gson()
                loading3.visibility = View.GONE
                val item: ListFilm = gson.fromJson(response, ListFilm::class.java)
                adapterLatestMovies = FilmListAdapter(item)
                recyclerViewLatestMovies.adapter = adapterLatestMovies
            },
            { error ->
                loading3.visibility = View.GONE
                Log.i("UiLovers", "OnErrorResponse:" + error.toString())
            })
        mRequestQueue.add(mStringRequest3)
    }

    private fun sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this)
        loading2.visibility = View.VISIBLE
        mStringRequest2 = StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres",
            { response ->
                val gson = Gson()
                loading2.visibility = View.GONE
                val catList: ArrayList<GenresItem> = gson.fromJson(response, object : TypeToken<ArrayList<GenresItem>>() {}.type)
                adapterCategory = CategoryListAdapter(catList)
                recyclerViewCategory.adapter = adapterCategory
            },
            { error ->
                loading2.visibility = View.GONE
                Log.i("UiLovers", "OnErrorResponse:" + error.toString())
            })
        mRequestQueue.add(mStringRequest2)
    }

    private fun banners() {
        val sliderItem: MutableList<SliderItem> = ArrayList()
        sliderItem.add(SliderItem(R.drawable.wide))
        sliderItem.add(SliderItem(R.drawable.wide1))
        sliderItem.add(SliderItem(R.drawable.wide3))

        viewPager2.adapter = SliderAdapter(sliderItem, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        })
        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.currentItem = 1
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                slideHandler.removeCallbacks(sliderRunnable)
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onPause() {
        super.onPause()
        slideHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        slideHandler.postDelayed(sliderRunnable, 2000)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.SliderPager)
        recyclerViewBestMovies = findViewById(R.id.view1)
        recyclerViewBestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewLatestMovies = findViewById(R.id.view3)
        recyclerViewLatestMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory = findViewById(R.id.view2)
        recyclerViewCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loading1 = findViewById(R.id.progressBar1)
        loading2 = findViewById(R.id.progressBar2)
        loading3 = findViewById(R.id.progressBar3)
    }
}

