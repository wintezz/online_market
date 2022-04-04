package com.example.online_market.DetailScreen.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.online_market.R

class PhoneImagesAdapter :
    RecyclerView.Adapter<PhoneImagesAdapter.PhoneImagesViewHolder>() {

    private val list: MutableList<String> = ArrayList()

    inner class PhoneImagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val phoneImageView: ImageView = itemView.findViewById(R.id.imgPhoneSlider)

        // прогресс бар для Glide
        private var circularProgressDrawable: CircularProgressDrawable =
            CircularProgressDrawable(itemView.context)

        init {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }

        fun bind(url: String) {
            Glide
                .with(itemView.context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(phoneImageView)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhoneImagesViewHolder(inflater.inflate(R.layout.item_phone_image, parent, false))
    }

    override fun onBindViewHolder(holder: PhoneImagesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}