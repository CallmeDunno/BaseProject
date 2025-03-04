package com.example.base.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.base.R

class TestDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bd = inflater.inflate(R.layout.mp_dialog_rename, container, false)

        bd.findViewById<TextView>(R.id.btnCancel).setOnClickListener{ Log.v("aa", "aaaaaaaa") }

        return bd
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
        }
        return dialog
    }

    override fun onResume() {
        super.onResume()
        dialog?.apply {
            val params = window?.attributes
            params?.width = ViewGroup.LayoutParams.MATCH_PARENT
            params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            view?.fitsSystemWindows = true
            window?.attributes = params as WindowManager.LayoutParams
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    override fun onStart() {
        super.onStart()
    }
}