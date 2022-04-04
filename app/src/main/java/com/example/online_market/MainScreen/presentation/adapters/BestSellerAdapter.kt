package com.example.online_market.MainScreen.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.online_market.App
import com.example.online_market.R
import com.example.online_market.MainScreen.repository.retrofit.entities.BestSeller

class BestSellerAdapter(
    private val onMovieItemClick: () -> Unit
) :
    RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {

    private val list: MutableList<BestSeller> = ArrayList()

    inner class BestSellerViewHolder(view: View, onMovieItemClick: () -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val phoneRootLayout: CardView = itemView.findViewById(R.id.cvPhoneRootLayout)
        private val phoneImageView: ImageView = itemView.findViewById(R.id.imgPhone)
        private val priceWithSaleTextView: TextView = itemView.findViewById(R.id.tvPriceWithSale)
        private val priceWithoutSaleTextView: TextView =
            itemView.findViewById(R.id.tvPriceWithoutSale)
        private val namePhoneTextView: TextView = itemView.findViewById(R.id.tvNamePhone)
        private val favoriteRadioButton: RadioButton =
            itemView.findViewById(R.id.radioFavoriteBestSeller)

        // прогресс бар для Glide
        private var circularProgressDrawable: CircularProgressDrawable =
            CircularProgressDrawable(itemView.context)
        private var isChecked: Boolean = false

        init {
            phoneRootLayout.setOnClickListener { onMovieItemClick.invoke() }
            favoriteRadioButton.setOnClickListener {
                if (isChecked) {
                    Log.d("radio_button", favoriteRadioButton.isChecked.toString())
                    favoriteRadioButton.isChecked = false
                    isChecked = false
                } else {
                    isChecked = true
                }
            }

            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }


        fun bind(bestSeller: BestSeller) {
            Glide
                .with(itemView.context)
                .load(bestSeller.picture)
                .placeholder(circularProgressDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(phoneImageView)
            favoriteRadioButton.isChecked = bestSeller.isFavorites
            isChecked = bestSeller.isFavorites
            priceWithSaleTextView.text =
                String.format(
                    itemView.context.getString(R.string.phone_price),
                    bestSeller.discountPrice.toString()
                )
            priceWithoutSaleTextView.text = String.format(
                itemView.context.getString(R.string.phone_price),
                bestSeller.priceWithoutDiscount.toString()
            )
            namePhoneTextView.text = bestSeller.title
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<BestSeller>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BestSellerViewHolder(
            inflater.inflate(R.layout.item_phone, parent, false),
            onMovieItemClick
        )
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
    }
