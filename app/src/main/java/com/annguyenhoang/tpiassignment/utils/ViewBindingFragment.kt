package com.annguyenhoang.tpiassignment.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding> : Fragment() {

    abstract val fragmentInflater: (
        inflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ) -> VB

    private var _binding: VB? = null
    val binding
        get() = checkNotNull(_binding) {
            "Cannot find the binding of this fragment"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = fragmentInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
        initControls()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun initViews(savedInstanceState: Bundle?) {}
    open fun initControls() {}

}