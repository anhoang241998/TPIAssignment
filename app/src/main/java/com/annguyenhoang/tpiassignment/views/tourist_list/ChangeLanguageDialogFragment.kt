package com.annguyenhoang.tpiassignment.views.tourist_list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.annguyenhoang.tpiassignment.databinding.DialogChangeLanguageBinding
import com.annguyenhoang.tpiassignment.views.tourist_list.adapters.LanguageAdapter
import com.annguyenhoang.tpiassignment.views.tourist_list.models.AppLanguage

class ChangeLanguageDialogFragment : DialogFragment() {

    private lateinit var languageAdapter: LanguageAdapter
    private var _binding: DialogChangeLanguageBinding? = null
    private val binding get() = _binding!!

    private var onChangeLanguageItemClicked: ((AppLanguage) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { notNullActivity ->
            _binding = DialogChangeLanguageBinding.inflate(notNullActivity.layoutInflater)
            val builder = AlertDialog.Builder(notNullActivity)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        initChangeLanguageRv()
        languageAdapter.setOnLanguageItemClicked { currLanguage ->
            onChangeLanguageItemClicked?.invoke(currLanguage)
            this.dismiss()
        }
    }

    fun onChangeLanguageItemClicked(onItemClicked: (AppLanguage) -> Unit) {
        this.onChangeLanguageItemClicked = onItemClicked
    }

    private fun initChangeLanguageRv() {
        binding.rvChangeLanguage.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            languageAdapter = LanguageAdapter()
            languageAdapter.submitList(buildStaticAppLanguage())
            adapter = languageAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val languageMap = mapOf(
            0 to "正體中文",
            1 to "zh-cn",
            2 to "en",
            3 to "ja",
            4 to "ko",
            5 to "es",
            6 to "id",
            7 to "th",
            8 to "vi"
        )

        fun buildStaticAppLanguage() = buildList {
            repeat(9) { index ->
                add(
                    AppLanguage(
                        id = index,
                        language = languageMap[index] ?: "Not found"
                    )
                )
            }
        }
    }

}