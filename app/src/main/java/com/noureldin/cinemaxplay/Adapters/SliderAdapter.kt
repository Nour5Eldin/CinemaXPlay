package com.noureldin.cinemaxplay.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.noureldin.cinemaxplay.Adapters.SliderAdapter.SliderViewHolder
import com.noureldin.cinemaxplay.Domain.SliderItem
import com.noureldin.cinemaxplay.R

class SliderAdapter(sliderItems: MutableList<SliderItem>, viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderViewHolder>() {
    private val sliderItems: List<SliderItem>
    private val viewPager2: ViewPager2
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.imageSlider)
        }

        fun setImage(sliderItem: SliderItem) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(60))
            Glide.with(context!!)
                .load(sliderItem.getImage())
                .apply(requestOptions)
                .into(imageView)
        }
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }
}