package com.example.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseBottomSheet<VB : ViewBinding>(context: Context) :
        BottomSheetDialog(context) {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = inflate()
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        disableDragging()
        initViews()
        initActions()
        setOnDismissListener { onDismiss() }
    }

    abstract fun inflate(): VB

    open fun initViews() {}
    open fun initActions() {}
    open fun onDismiss() {}

    private fun disableDragging() {
        val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.isDraggable = false
        }
    }

}