package com.example.online_market.CartScreen.presentation.fragments.tab_layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.online_market.R
import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponseItem


class ShopItemTabLayoutFragment : Fragment() {

    private var detailInfoResponseItem: DetailInfoResponseItem? = null
    private lateinit var cpuTextView: TextView
    private lateinit var cameraTextView: TextView
    private lateinit var ramTextView: TextView
    private lateinit var sdTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            detailInfoResponseItem = it.getParcelable(DETAIL_ITEM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        setData()
    }

    private fun setData() {
        detailInfoResponseItem?.let {
            cpuTextView.text = it.cPU
            cameraTextView.text = it.camera
            ramTextView.text = it.ssd
            sdTextView.text = it.sd
        }
    }

    private fun initView(view: View) {
        cpuTextView = view.findViewById(R.id.tvCpu)
        cameraTextView = view.findViewById(R.id.tvCamera)
        ramTextView = view.findViewById(R.id.tvRam)
        sdTextView = view.findViewById(R.id.tvSd)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_item_tab_layout, container, false)
    }

    companion object {
        fun newInstance(detailInfoResponseItem: DetailInfoResponseItem) =
            ShopItemTabLayoutFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DETAIL_ITEM, detailInfoResponseItem)
                }
            }

        private const val DETAIL_ITEM = "detailItem"
    }
}