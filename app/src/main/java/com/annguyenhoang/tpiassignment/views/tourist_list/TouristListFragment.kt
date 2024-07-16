package com.annguyenhoang.tpiassignment.views.tourist_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.core.ui.VerticalSpacerDecoration
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristListBinding
import com.annguyenhoang.tpiassignment.utils.UiText
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment
import com.annguyenhoang.tpiassignment.utils.gone
import com.annguyenhoang.tpiassignment.utils.observeFlow
import com.annguyenhoang.tpiassignment.utils.show
import com.annguyenhoang.tpiassignment.views.tourist_list.adapters.TouristsAdapter
import com.annguyenhoang.tpiassignment.views.tourist_list.models.Tourist
import com.annguyenhoang.tpiassignment.views.tourist_list.models.TouristLoadMore
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class TouristListFragment : ViewBindingFragment<FragmentTouristListBinding>(), MenuProvider {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristListBinding
        get() = FragmentTouristListBinding::inflate

    override val screenName: String
        get() = activity?.getString(R.string.tourists_list_fragment_title) ?: ""

    private val viewModel: TouristListViewModel by viewModel()
    private lateinit var touristsAdapter: TouristsAdapter
    private var changeLanguageDialog: ChangeLanguageDialogFragment? = null

    private var skeletonTourists: Skeleton? = null

    private var isLoadMore = false

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setUpRvTourist()

        observeFlow(viewModel.uiState) { state ->
            val (isLoading, tourists, error, errorMsgFromServer, _, _, _, isLoadingMore) = state

            if (isLoading) {
                showSkeletonTouristsLoading()
                return@observeFlow
            }

            isLoadMore = isLoadingMore

            if (isLoadMore) {
                showLoadMoreProgressIndicator(tourists)
                return@observeFlow
            }

            hideSkeletonTouristsLoading()

            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }

            if (error != null || errorMsgFromServer != null) {
                error?.let {
                    val msg = context?.getString((error as UiText.StringResource).id) ?: "Others"
                    showErrorAlertDialog(msg)
                } ?: showErrorAlertDialog(errorMsgFromServer ?: "Others")
            }

            if (tourists.isNotEmpty()) {
                touristsAdapter.submitList(tourists)
            }
        }
    }

    override fun initControls() {
        super.initControls()
        touristsAdapter.setOnItemClicked { currTourist ->
            val action = TouristListFragmentDirections.actionTouristListFragmentToTouristDetailFragment(
                currTourist
            )
            findNavController().navigate(action)
        }

        binding.rvTourists.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isLoadMore) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    viewModel.loadMoreTourists()
                }
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAttractionsTourists(
                lang = viewModel.currentSelectedLang,
                forceRefresh = true,
            )
        }
    }

    override fun initializeToolBar() {
        super.initializeToolBar()
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpRvTourist() {
        binding.skeletonLayout.setOnTouchListener { _, _ -> true }
        binding.rvTourists.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(20)

            touristsAdapter = TouristsAdapter()
            adapter = touristsAdapter

            addItemDecoration(VerticalSpacerDecoration)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_tourist_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.ic_switch_language -> {
                showLanguageDialog()
                changeLanguageDialog?.onChangeLanguageItemClicked { currLanguage ->
                    val lang = if (currLanguage.language == "正體中文") {
                        "zh-tw"
                    } else {
                        currLanguage.language
                    }

                    viewModel.getAttractionsTourists(lang)
                }
                true
            }

            else -> false
        }
    }

    private fun showLanguageDialog() {
        changeLanguageDialog = ChangeLanguageDialogFragment()
        changeLanguageDialog?.show(parentFragmentManager, "ChangeLanguageDialogFragment")
    }

    private fun showSkeletonTouristsLoading() {
        binding.skeletonLayout.show()
        binding.rvTourists.gone()

        skeletonTourists = binding.skeletonLayout.createSkeleton()
        skeletonTourists?.showSkeleton()
    }

    private fun hideSkeletonTouristsLoading() {
        skeletonTourists?.showOriginal()
        binding.skeletonLayout.gone()
        binding.rvTourists.show()
    }

    private fun showLoadMoreProgressIndicator(tourists: List<Tourist>) {
        val loadMoreItem = TouristLoadMore(id = "${UUID.randomUUID()}:${System.currentTimeMillis()}")
        val currTouristsWithLoadMoreItem = tourists + loadMoreItem
        touristsAdapter.submitList(currTouristsWithLoadMoreItem)
    }

    private fun showErrorAlertDialog(errMsg: String) {
        val alertTitle = activity?.getString(R.string.error_dialog_title) ?: "Error"
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(alertTitle)
            .setMessage(errMsg)
            .setPositiveButton(
                R.string.error_dialog_btn_title
            ) { p0, _ -> p0.dismiss() }

        alertDialog.show()
    }

}