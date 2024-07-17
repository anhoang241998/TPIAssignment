package com.annguyenhoang.tpiassignment.views.tourist_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.annguyenhoang.tpiassignment.databinding.FragmentTouristWebviewBinding
import com.annguyenhoang.tpiassignment.utils.ViewBindingFragment

@SuppressLint("SetJavaScriptEnabled")
class TouristWebViewFragment : ViewBindingFragment<FragmentTouristWebviewBinding>() {

    override val fragmentInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTouristWebviewBinding
        get() = FragmentTouristWebviewBinding::inflate

    private val args by navArgs<TouristWebViewFragmentArgs>()

    override val screenName: String
        get() = ""

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        binding.webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(args.url)
        }
    }

}