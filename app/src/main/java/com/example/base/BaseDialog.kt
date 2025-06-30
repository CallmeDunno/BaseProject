package com.example.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<VB : ViewBinding>(private val context: Context) : Dialog(context, R.style.CustomDialog) {
    protected lateinit var binding: VB
    abstract val layoutId: Int
    abstract val isCancel: Boolean
    abstract val cancelTouchOutside: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
        setContentView(binding.root)
        setCancelable(isCancel)
        this.setCanceledOnTouchOutside(cancelTouchOutside)
        super.onCreate(savedInstanceState)
        initView()
        initAction()
        this.setOnDismissListener {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            onDismissListener()
        }
    }

    abstract fun initView()
    abstract fun initAction()
    abstract fun onDismissListener()

}