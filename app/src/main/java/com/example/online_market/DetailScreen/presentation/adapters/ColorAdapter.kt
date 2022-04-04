package com.example.online_market.DetailScreen.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import at.markushi.ui.CircleButton
import com.example.online_market.R


class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedItem: Int = 0
    private val list: MutableList<String> = ArrayList()

    inner class ColorViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val colorCircleButton: CircleButton = itemView.findViewById(R.id.btnColor)
        private val iconSelect: ImageView = itemView.findViewById(R.id.iconSelectedImageView)
        private val viewDark: ImageView = itemView.findViewById(R.id.viewBlackSelected)

        init {
            colorCircleButton.setOnClickListener {
                selectedItem = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun bind(color: String, position: Int) {
            colorCircleButton.setColor(android.graphics.Color.parseColor(color))
            if (selectedItem == position) {
                viewDark.visibility = View.VISIBLE
                iconSelect.visibility = View.VISIBLE
            } else {
                viewDark.visibility = View.INVISIBLE
                iconSelect.visibility = View.INVISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ColorViewHolder(
            inflater.inflate(R.layout.item_color, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}