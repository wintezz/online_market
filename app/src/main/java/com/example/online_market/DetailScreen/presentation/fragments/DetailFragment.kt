package com.example.online_market.DetailScreen.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.online_market.R
import com.example.online_market.DetailScreen.domain.DetailViewModel
import com.example.online_market.DetailScreen.presentation.adapters.ColorAdapter
import com.example.online_market.DetailScreen.presentation.adapters.DetailViewPagerAdapter
import com.example.online_market.DetailScreen.presentation.adapters.MemoryRadioBtnAdapter
import com.example.online_market.DetailScreen.presentation.adapters.PhoneImagesAdapter
import com.example.online_market.DetailScreen.repository.retrofit.entities.DetailInfoResponseItem
import com.example.online_market.helpers.FragmentClickListener
import com.example.online_market.helpers.ViewStateScreen
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private lateinit var mainFragmentRootLayout: CoordinatorLayout
    private lateinit var progressBarLayout: FrameLayout
    private lateinit var detailBottomSheetBehavior: CardView
    private lateinit var backButton: Button
    private lateinit var cartButton: Button
    private lateinit var phonePhotosViewPager2: ViewPager2
    private lateinit var namePhoneTextView: TextView
    private lateinit var favoriteButton: MaterialButton
    private lateinit var phoneRatingBar: RatingBar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var rvColor: RecyclerView
    private lateinit var rvMemory: RecyclerView
    private lateinit var addCartButton: Button
    private val detailViewModel by viewModel<DetailViewModel>()
    private val phoneImagesAdapter = PhoneImagesAdapter()
    private lateinit var detailViewPagerAdapter: DetailViewPagerAdapter
    private lateinit var memoryRadioBtnAdapter: MemoryRadioBtnAdapter
    private lateinit var colorAdapter: ColorAdapter
    private var fragmentClickListener: FragmentClickListener? = null
    private var isFavorite: Boolean = false
    private lateinit var errorLayout: FrameLayout
    private lateinit var retry: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        // показываем прогрес бар
        showProgressBar()
        initListener()
        initRvAndVp()
        initTabLayout()
        initSubscribe()

        detailViewModel.getDetailInfo(getString(R.string.api_key))
    }

    private fun initTabLayout() {
        tabLayout.apply {
            addTab(this.newTab().setText("Shop"))
            addTab(this.newTab().setText("Details"))
            addTab(this.newTab().setText("Features"))
        }
    }

    private fun initRvAndVp() {
        phonePhotosViewPager2.adapter = phoneImagesAdapter
        memoryRadioBtnAdapter = MemoryRadioBtnAdapter {

        }
        rvMemory.adapter = memoryRadioBtnAdapter
        colorAdapter = ColorAdapter()
        rvColor.adapter = colorAdapter
    }

    private fun initSubscribe() {
        detailViewModel.detailInfo.observe(viewLifecycleOwner, Observer(::setDetailData))
        detailViewModel.viewState.observe(viewLifecycleOwner, Observer(::setViewState))
    }

    private fun setDetailData(detailInfoResponseItem: DetailInfoResponseItem) {
        phoneImagesAdapter.initData(detailInfoResponseItem.images)
        namePhoneTextView.text = detailInfoResponseItem.title
        phoneRatingBar.rating = detailInfoResponseItem.rating.toFloat()
        viewPager.adapter =
            DetailViewPagerAdapter(parentFragmentManager, lifecycle, detailInfoResponseItem)
        memoryRadioBtnAdapter.initData(detailInfoResponseItem.capacity)
        addCartButton.text =
            String.format(
                getString(R.string.phone_price_add_cart_button),
                detailInfoResponseItem.price
            )
        colorAdapter.initData(detailInfoResponseItem.color)
        favoriteButton.apply {
            isFavorite = detailInfoResponseItem.isFavorites
            if (isFavorite) {
                setIconResource(R.drawable.ic_favorite_on_detail)
            } else {
                setIconResource(R.drawable.ic_favorite_off_detail)
            }
        }
    }

    private fun initView(view: View) {
        mainFragmentRootLayout = view.findViewById(R.id.mainFragmentRootLayout)
        backButton = view.findViewById(R.id.btnBack)
        cartButton = view.findViewById(R.id.btnCart)
        phonePhotosViewPager2 = view.findViewById(R.id.vp2PhonePhotos)
        namePhoneTextView = view.findViewById(R.id.tvNamePhone)
        favoriteButton = view.findViewById(R.id.btnFavorite)
        phoneRatingBar = view.findViewById(R.id.rbPhone)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.vp2DetailPhone)
        rvColor = view.findViewById(R.id.rvSelectColor)
        rvMemory = view.findViewById(R.id.rvSelectMemory)
        addCartButton = view.findViewById(R.id.btnAddCart)
        progressBarLayout = view.findViewById(R.id.progressBarLayout)
        detailBottomSheetBehavior = view.findViewById(R.id.bottom_sheet_detail_phone)
        errorLayout = view.findViewById(R.id.error_layout)
        retry = view.findViewById(R.id.retryBtn)
    }

    private fun initListener() {
        // слушатель на клики по табу
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        // слушатель на свайп по viewPager и сет корректного таба исходя из видимого фрагмента
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.apply {
                    selectTab(this.getTabAt(position))
                }
            }
        })
        // кнопка назад
        backButton.setOnClickListener {
            fragmentClickListener?.onBackFragmentClick()
        }
        // кнопка корзина
        cartButton.setOnClickListener {
            fragmentClickListener?.onOpenCartFragmentClick()
        }
        favoriteButton.setOnClickListener {
            isFavorite = if (isFavorite) {
                favoriteButton.setIconResource(R.drawable.ic_favorite_off_detail)
                false
            } else {
                favoriteButton.setIconResource(R.drawable.ic_favorite_on_detail)
                true
            }
        }
        retry.setOnClickListener {
            detailViewModel.getDetailInfo(getString(R.string.api_key))
            errorLayout.visibility = View.INVISIBLE
        }
    }

    private fun setViewState(viewStateScreen: ViewStateScreen) = with(viewStateScreen) {
        if (isDownloaded)
            hideProgressBar()
        else if (e != null)
            showException(e)
    }

    private fun showProgressBar() {
        progressBarLayout.visibility = View.VISIBLE
        detailBottomSheetBehavior.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        progressBarLayout.visibility = View.INVISIBLE
        detailBottomSheetBehavior.visibility = View.VISIBLE
    }

    private fun showException(e: Throwable) {
        errorLayout.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentClickListener)
            fragmentClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        fragmentClickListener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}