package com.example.online_market.MainActivity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.online_market.CartScreen.domain.CartViewModel
import com.example.online_market.CartScreen.repository.retrofit.entities.CartResponseItem
import com.example.online_market.R
import com.example.online_market.helpers.FragmentClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), FragmentClickListener {

    private lateinit var navController: NavController
    private val cartViewModel by viewModel<CartViewModel>()
    private lateinit var cartImageView: ImageView
    private lateinit var countItemsCartTextView: TextView
    private lateinit var bottomNavigationView: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initListener()
        initSubscribe()

        cartViewModel.getDetailInfo(getString(R.string.api_key))
    }

    private fun initListener() {
        cartImageView.setOnClickListener {
            navController.navigate(R.id.cartFragment)
            bottomNavigationView.background =
                ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout)
        }
    }

    private fun initView() {
        navController = findNavController(R.id.nav_host)
        cartImageView = findViewById(R.id.imgCart)
        countItemsCartTextView = findViewById(R.id.tvCountItemsCart)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }

    private fun initSubscribe() {
        cartViewModel.cartInfo.observe(this, Observer(::setCountItemCart))
    }

    private fun setCountItemCart(cartResponseItem: CartResponseItem) {
        if (cartResponseItem.basket.isNotEmpty()) {
            countItemsCartTextView.visibility = View.VISIBLE
            countItemsCartTextView.text = cartResponseItem.basket.size.toString()
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.detailFragment) {
            navController.popBackStack()
            bottomNavigationView.background =
                ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout_rounded)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOpenDetailFragmentClick() {
        navController.navigate(R.id.detailFragment)
        bottomNavigationView.background =
            ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout_rounded)
    }

    override fun onBackFragmentClick() {
        if (navController.currentDestination?.id == R.id.detailFragment) {
            navController.navigate(R.id.mainFragment)
            bottomNavigationView.background =
                ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout_rounded)
        } else if (navController.currentDestination?.id == R.id.cartFragment
            && navController.previousBackStackEntry?.destination?.id == R.id.detailFragment
        ) {
            navController.navigate(R.id.action_cart_fragment_to_detail_fragment)
            bottomNavigationView.background =
                ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout_rounded)
        } else if (navController.currentDestination?.id == R.id.cartFragment
            && navController.previousBackStackEntry?.destination?.id == R.id.mainFragment
        ) {
            navController.popBackStack()
            bottomNavigationView.background =
                ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout_rounded)
        }
    }

    override fun onOpenCartFragmentClick() {
        navController.navigate(R.id.cartFragment)
        bottomNavigationView.background =
            ContextCompat.getDrawable(this, R.drawable.bg_bottom_navigation_layout)
    }
}