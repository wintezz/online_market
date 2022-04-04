package com.example.online_market.MainScreen.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.online_market.App
import com.example.online_market.R
import com.example.online_market.MainScreen.repository.retrofit.entities.HomeStore


class HomeStoreAdapter :
    RecyclerView.Adapter<HomeStoreAdapter.HomeStoreViewHolder>() {

    private val list: MutableList<HomeStore> = ArrayList()

    inner class HomeStoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val slideImageView: ImageView = itemView.findViewById(R.id.imgSlideHomeStore)
        private val newImageView: ImageView = itemView.findViewById(R.id.imgNew)
        private val titleSlideTextView: TextView = itemView.findViewById(R.id.tvTitleSlideHomeStore)
        private val subTitleSlideTextView: TextView =
            itemView.findViewById(R.id.tvSubTitleSlideHomeStore)

        // прогресс бар для Glide
        private var circularProgressDrawable: CircularProgressDrawable =
            CircularProgressDrawable(itemView.context)

        init {
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }

        fun bind(homeStore: HomeStore) {
            Glide
                .with(itemView.context)
                .load(homeStore.picture)
                .placeholder(circularProgressDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(slideImageView)
            if (homeStore.isNew != null && homeStore.isNew) {
                newImageView.visibility = View.VISIBLE
            } else {
                newImageView.visibility = View.INVISIBLE
            }
            titleSlideTextView.text = homeStore.title
            subTitleSlideTextView.text = homeStore.subtitle
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<HomeStore>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeStoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeStoreViewHolder(inflater.inflate(R.layout.item_home_store, parent, false))
    }

    override fun onBindViewHolder(holder: HomeStoreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}