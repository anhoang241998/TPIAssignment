package com.annguyenhoang.tpiassignment.views.tourist_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristDetailBinding
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment

class TouristDetailFragment : ViewBindingFragment<FragmentTouristDetailBinding>() {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristDetailBinding
        get() = FragmentTouristDetailBinding::inflate

}