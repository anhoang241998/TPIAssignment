package com.annguyenhoang.tpiassignment.views.tourist_list

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.core.ui.VerticalSpacerDecoration
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristListBinding
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment
import com.annguyenhoang.tpiassignment.views.tourist_list.adapters.TouristsAdapter
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TouristListFragment : ViewBindingFragment<FragmentTouristListBinding>(), MenuProvider {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristListBinding
        get() = FragmentTouristListBinding::inflate

    private val viewModel: TouristListViewModel by viewModel()
    private lateinit var touristsAdapter: TouristsAdapter
    private var changeLanguageDialog: ChangeLanguageDialogFragment? = null
    private var skeleton: Skeleton? = null

    override fun initViews() {
        super.initViews()
        setUpToolBar()
        setUpRvTourist()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val (isLoading, tourists, error, errorMsgFromServer) = state

                    if (isLoading) {
                        binding.rvTourists.layoutManager = object : LinearLayoutManager(context) {
                            override fun canScrollVertically(): Boolean {
                                return false
                            }
                        }

                        skeleton = binding.rvTourists.applySkeleton(R.layout.item_tourist, 10)
                        skeleton?.showSkeleton()
                        return@collect
                    }

                    skeleton?.showOriginal()
                    binding.rvTourists.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false,
                    )

                    if (tourists.isNotEmpty()) {
                        touristsAdapter.submitList(emptyList())
                        touristsAdapter.submitList(tourists.toList())
                    }
                }
            }
        }
    }

    private fun setUpToolBar() {
        val screenName = activity?.getString(R.string.tourists_list_fragment_title) ?: ""
        (activity as? AppCompatActivity)?.supportActionBar?.title = screenName
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setUpRvTourist() {
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

                    viewModel.getAllAttractions(lang)
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

}