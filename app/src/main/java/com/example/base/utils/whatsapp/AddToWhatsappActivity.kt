package com.example.base.utils.whatsapp

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.example.base.BaseActivity
import com.example.base.R

abstract class AddToWhatsappActivity<DB : ViewDataBinding> : BaseActivity<DB>() {

    fun addToWhatsapp(sp: StickerPack) {
        if (sp.stickers.size in 3..30) {
            addStickerPackageToWhatsApp(sp)
        } else {
            showErrorDialog()
        }
    }

    @Deprecated("Deprecated in Java") override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PACK_REQUEST) {
            if (resultCode == Activity.RESULT_CANCELED && data != null) {
                val validationError = data.getStringExtra("validation_error")
                if (validationError != null) {
                    Log.e("CHECK_BUG", "Validation failed:$validationError")
                }
            } else {
                Log.d("CHECK_BUG", "Add StickerPack to WhatsApp request received $resultCode")
            }
        }
    }

    private fun addStickerPackageToWhatsApp(sp: StickerPack) {
        val intent = Intent()
        intent.action = "com.whatsapp.intent.action.ENABLE_STICKER_PACK"
        intent.putExtra(EXTRA_STICKER_PACK_ID, sp.identifier)
        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, WhitelistCheck.CONTENT_PROVIDER_AUTHORITY)
        intent.putExtra(EXTRA_STICKER_PACK_NAME, sp.name)
        try {
            startActivityForResult(intent, ADD_PACK_REQUEST)
        } catch (e: ActivityNotFoundException) {
//            notify(getString(R.string.error_adding_sticker_pack))
        }
    }

    fun showErrorDialog() {
        val alertDialog = AlertDialog.Builder(this).setNegativeButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }.create()
        alertDialog.setTitle(this.getString(R.string.invalid_action))
        alertDialog.setMessage(this.getString(R.string.invalid_action_msg))
        alertDialog.show()
    }

    private companion object {
        const val ADD_PACK_REQUEST = 200
        const val EXTRA_STICKER_PACK_ID = "sticker_pack_id"
        const val EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority"
        const val EXTRA_STICKER_PACK_NAME = "sticker_pack_name"
    }
}