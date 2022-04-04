package com.example.online_market.CartScreen.presentation.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.online_market.R
import CartScreen.domain.retrofit.entities.Basket


class CartAdapter(private val onTotalPriceChange: (Int) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val list: MutableList<Basket> = ArrayList()

    inner class CartViewHolder(view: View, onTotalPriceChange: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val itemPhotoImageView: ImageView = itemView.findViewById(R.id.imgProduct)
        private val nameProductTextView: TextView = itemView.findViewById(R.id.tvNameProduct)
        private val priceProductTextView: TextView = itemView.findViewById(R.id.tvPriceProduct)
        private val minusCountImageButton: Button = itemView.findViewById(R.id.btnMinusCount)
        private val plusCountImageButton: Button = itemView.findViewById(R.id.btnPlusCount)
        private val countTextView: TextView = itemView.findViewById(R.id.tvCountProduct)
        private val removeItemButton: Button = itemView.findViewById(R.id.btnRemoveCount)
        private lateinit var itemCart: Basket
        private var totalPrice: Int = 0

        init {
            minusCountImageButton.setOnClickListener {
                if (countTextView.text.toString().toInt() != 1) {
                    countTextView.text = countTextView.text.toString().toInt().minus(1).toString()
                    totalPrice -= itemCart.price
                    Log.d("change_price", "total = $totalPrice")
                    onTotalPriceChange.invoke(totalPrice)
                }
            }
            plusCountImageButton.setOnClickListener {
                countTextView.text = countTextView.text.toString().toInt().plus(1).toString()
                totalPrice += itemCart.price
                Log.d("change_price", "total = $totalPrice")
                onTotalPriceChange.invoke(totalPrice)
            }
            removeItemButton.setOnClickListener {
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                totalPrice -= itemCart.price * countTextView.text.toString().toInt()
                Log.d("change_price", "total = $totalPrice")
                onTotalPriceChange.invoke(totalPrice)
            }
        }

        fun bind(basket: Basket) {
            itemCart = basket
            totalPrice += basket.price
            Log.d("change_price", "total = $totalPrice")
            Glide
                .with(itemView.context)
                .load(basket.images)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(RoundedCorners(10))
                .centerCrop()
                .into(itemPhotoImageView)
            countTextView.text = "1"
            nameProductTextView.text = basket.title
            priceProductTextView.text =
                String.format(
                    itemView.context.getString(R.string.price_item_cart),
                    basket.price.toDouble()
                )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initData(list: List<Basket>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CartViewHolder(
            inflater.inflate(R.layout.item_cart, parent, false),
            onTotalPriceChange
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}