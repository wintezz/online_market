package com.example.online_market.MainScreen.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.online_market.R
import com.example.online_market.MainScreen.repository.data.CategoryDto

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedItem: Int = 0
    private val list: MutableList<CategoryDto> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var categoryItemCardView: CardView =
            itemView.findViewById(R.id.categoryItemCardView)
        private val categoryButton: ImageView = itemView.findViewById(R.id.iconCategory)
        private val nameCategoryTextView: TextView = itemView.findViewById(R.id.tvNameCategory)

        // colors
        private val orangeColor = ContextCompat.getColor(itemView.context, R.color.orange)
        private val whiteColor = ContextCompat.getColor(itemView.context, R.color.white)
        private val cyanDarkColor = ContextCompat.getColor(itemView.context, R.color.cyan_dark)

        init {
            categoryItemCardView.setOnClickListener {
                selectedItem = adapterPosition
                notifyDataSetChanged()
            }
        }

        fun bind(categoryItem: CategoryDto, position: Int) {
            categoryButton.setImageResource(categoryItem.icon)
            nameCategoryTextView.text = categoryItem.name
            if (selectedItem == position) {
                categoryButton.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                categoryItemCardView.setCardBackgroundColor(orangeColor)
                nameCategoryTextView.setTextColor(orangeColor)
            } else {
                categoryButton.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray_200
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                categoryItemCardView.setCardBackgroundColor(whiteColor)
                nameCategoryTextView.setTextColor(cyanDarkColor)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<CategoryDto>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(inflater.inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}