package com.example.online_market.DetailScreen.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.online_market.CartScreen.presentation.fragments.tab_layout.DetailItemTabLayoutFragment
import com.example.online_market.CartScreen.presentation.fragments.tab_layout.FeaturesItemTabLayoutFragment
import com.example.online_market.CartScreen.presentation.fragments.tab_layout.ShopItemTabLayoutFragment
import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponseItem

class DetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val detailInfoResponseItem: DetailInfoResponseItem
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShopItemTabLayoutFragment.newInstance(detailInfoResponseItem)
            1 -> DetailItemTabLayoutFragment.newInstance()
            else -> FeaturesItemTabLayoutFragment.newInstance()
        }
    }
}