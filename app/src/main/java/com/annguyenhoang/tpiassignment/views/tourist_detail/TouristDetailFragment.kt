package com.annguyenhoang.tpiassignment.views.tourist_detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristDetailBinding
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment
import com.annguyenhoang.tpiassignment.utils.toPixel
import com.annguyenhoang.tpiassignment.views.tourist_detail.TouristDetailFragmentDirections.Companion.actionTouristDetailFragmentToTouristWebViewFragment

class TouristDetailFragment : ViewBindingFragment<FragmentTouristDetailBinding>() {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristDetailBinding
        get() = FragmentTouristDetailBinding::inflate

    private val args by navArgs<TouristDetailFragmentArgs>()

    override val screenName: String
        get() = args.touristDetail.touristTitle

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        paddingSafeView()
        mapDetailsArgToViews()
    }

    private fun paddingSafeView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.detailContainer) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = insets.bottom + (context?.toPixel(12f)?.toInt() ?: 0))
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun mapDetailsArgToViews() {
        args.touristDetail.apply {
            if (imageUrl.isNotEmpty()) {
                binding.imgTourist.load(imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.img_loading)
                }
            }

            binding.touristTitle.text = touristTitle
            binding.touristDescription.text = touristDescription

            if (touristUrl.isNotEmpty()) {
                val spannableString = SpannableString(touristUrl)
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val action = actionTouristDetailFragmentToTouristWebViewFragment(touristUrl)
                        findNavController().navigate(action)
                    }
                }

                spannableString.setSpan(clickableSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.linkTextView.text = spannableString
                binding.linkTextView.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

}