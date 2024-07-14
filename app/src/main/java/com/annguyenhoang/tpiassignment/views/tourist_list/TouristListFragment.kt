package com.annguyenhoang.tpiassignment.views.tourist_list

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristListBinding
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment

class TouristListFragment : ViewBindingFragment<FragmentTouristListBinding>(), MenuProvider {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristListBinding
        get() = FragmentTouristListBinding::inflate

    private var changeLanguageDialog: ChangeLanguageDialogFragment? = null

    override fun initViews() {
        super.initViews()
        setUpToolBar()
    }

    override fun initControls() {
        super.initControls()
    }

    private fun setUpToolBar() {
        val screenName = activity?.getString(R.string.tourists_list_fragment_title) ?: ""
        (activity as? AppCompatActivity)?.supportActionBar?.title = screenName
        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_tourist_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.ic_switch_language -> {
                showLanguageDialog()
                changeLanguageDialog?.onChangeLanguageItemClicked { currLanguage ->

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