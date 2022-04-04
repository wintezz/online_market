package com.example.online_market.MainScreen.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.online_market.R
import com.example.online_market.helpers.FragmentClickListener
import com.example.online_market.helpers.ViewStateScreen
import com.example.online_market.MainScreen.domain.MainViewModel
import com.example.online_market.MainScreen.presentation.adapters.BestSellerAdapter
import com.example.online_market.MainScreen.presentation.adapters.CategoryAdapter
import com.example.online_market.MainScreen.presentation.adapters.HomeStoreAdapter
import com.example.online_market.MainScreen.presentation.adapters.item_decoration.GridSpacingItemDecoration
import com.example.online_market.MainScreen.presentation.adapters.item_decoration.SpacesItemDecoration
import com.example.online_market.MainScreen.repository.data.CategoryDto
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var mainFragmentRootLayout: ConstraintLayout
    private lateinit var rvCategory: RecyclerView
    private lateinit var rvPhones: RecyclerView
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var homeStoreViewPager2: ViewPager2
    private val adapterCategory = CategoryAdapter()
    private lateinit var adapterHomeStore: HomeStoreAdapter
    private lateinit var adapterBestSeller: BestSellerAdapter
    private lateinit var progressBarLayout: FrameLayout
    private lateinit var filtersBottomSheetDialog: BottomSheetDialog
    private lateinit var filtersBottomSheetView: View
    private lateinit var brandAutoCompleteTextView: AutoCompleteTextView
    private lateinit var sizeAutoCompleteTextView: AutoCompleteTextView
    private lateinit var priceAutoCompleteTextView: AutoCompleteTextView
    private lateinit var filtersButton: ImageButton
    private lateinit var closeFilterButton: Button
    private lateinit var doneFilterButton: Button
    private lateinit var errorLayout: FrameLayout
    private lateinit var retry: Button
    private var fragmentClickListener: FragmentClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initBottomSheetDialog(view)
        initListener()
        // показываем прогрес бар
        showProgressBar()
        initRv()
        initSubscribe()
        loadData()
    }

    private fun initListener() {
        filtersButton.setOnClickListener {
            filtersBottomSheetDialog.show()
        }
        closeFilterButton.setOnClickListener {
            filtersBottomSheetDialog.dismiss()
        }
        doneFilterButton.setOnClickListener {
            filtersBottomSheetDialog.dismiss()
        }
        retry.setOnClickListener {
            mainViewModel.getMainInfo(getString(R.string.api_key))
            errorLayout.visibility = View.INVISIBLE
        }
    }

    private fun initBottomSheetDialog(view: View) {
        filtersBottomSheetDialog = BottomSheetDialog(view.context, R.style.BottomSheetDialogTheme)
        filtersBottomSheetView = LayoutInflater.from(context).inflate(
            R.layout.filters_bottom_sheet_dialog_layout,
            view.findViewById(R.id.filtersRootLayout)
        )
        closeFilterButton = filtersBottomSheetView.findViewById(R.id.btnClose)
        doneFilterButton = filtersBottomSheetView.findViewById(R.id.btnDoneFilters)
        filtersBottomSheetDialog.setContentView(filtersBottomSheetView)
        //init view BottomSheetDialog
        brandAutoCompleteTextView =
            filtersBottomSheetView.findViewById(R.id.brandAutoCompleteTextView)
        priceAutoCompleteTextView =
            filtersBottomSheetView.findViewById(R.id.priceAutoCompleteTextView)
        sizeAutoCompleteTextView =
            filtersBottomSheetView.findViewById(R.id.sizeAutoCompleteTextView)
        // string arrays
        val brandArray = resources.getStringArray(R.array.brand_list)
        val priceArray = resources.getStringArray(R.array.price_list)
        val sizeArray = resources.getStringArray(R.array.size_list)
        // set adapters
        brandAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                view.context,
                R.layout.dropdown_item,
                brandArray
            )
        )
        priceAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                view.context,
                R.layout.dropdown_item,
                priceArray
            )
        )
        sizeAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                view.context,
                R.layout.dropdown_item,
                sizeArray
            )
        )
    }

    private fun initRv() {
        rvCategory.adapter = adapterCategory
        adapterHomeStore = HomeStoreAdapter()
        adapterBestSeller = BestSellerAdapter {
            fragmentClickListener?.onOpenDetailFragmentClick()
        }
        homeStoreViewPager2.adapter = adapterHomeStore
        rvPhones.apply {
            adapter = adapterBestSeller
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(12))
        }
    }

    private fun loadData() {
        // загружаем данные
        mainViewModel.getCategory()
        mainViewModel.getMainInfo(getString(R.string.api_key))
    }

    private fun initView(view: View) {
        mainFragmentRootLayout = view.findViewById(R.id.mainFragmentRootLayout)
        rvCategory = view.findViewById(R.id.rvCategory)
        rvPhones = view.findViewById(R.id.rvPhones)
        homeStoreViewPager2 = view.findViewById(R.id.vp2HomeStore)
        progressBarLayout = view.findViewById(R.id.progressBarLayout)
        filtersButton = view.findViewById(R.id.btnFiltersShow)
        errorLayout = view.findViewById(R.id.error_layout)
        retry = view.findViewById(R.id.retryBtn)
    }

    private fun initCategoryData(listCategory: List<CategoryDto>) {
        adapterCategory.initData(listCategory)
        if (rvCategory.itemDecorationCount == 0) {
            rvCategory.addItemDecoration(
                SpacesItemDecoration(
                    spaceRight = 25,
                    spaceLeft = 15,
                    size = listCategory.size
                )
            )
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
    }

    private fun hideProgressBar() {
        progressBarLayout.visibility = View.INVISIBLE
    }

    private fun showException(e: Throwable) {
        errorLayout.visibility = View.VISIBLE
    }

    private fun initSubscribe() {
        mainViewModel.categoryList.observe(viewLifecycleOwner, Observer(::initCategoryData))
        mainViewModel.homeStore.observe(viewLifecycleOwner, Observer(adapterHomeStore::initData))
        mainViewModel.bestSeller.observe(viewLifecycleOwner, Observer(adapterBestSeller::initData))
        mainViewModel.viewState.observe(viewLifecycleOwner, Observer(::setViewState))
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
        fun newInstance() = MainFragment()
    }
}