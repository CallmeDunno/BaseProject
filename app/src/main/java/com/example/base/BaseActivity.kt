package com.example.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Suppress("DEPRECATION")
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    abstract val layoutID: Int
    protected lateinit var TAG: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        binding = DataBindingUtil.setContentView(this, layoutID)
        TAG = "${this.localClassName} ===>"
        setContentView(binding.root)
        initView()
        onObserver()
        initAction()
    }

    override fun onResume() {
        super.onResume()
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //đen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //trắng
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    open fun setLanguage() {}

    open fun initView() {
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //đen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY //trắng

        //setLayoutNoLimit
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    open fun initAction() {}

    open fun onObserver() {}

    open fun handleEvent(event: Any?) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEvent(event: Any?) {
        handleEvent(event)
    }

    fun sendEvent(event: Any?) {
        requireNotNull(event) { "Object event can not be null" }
        EventBus.getDefault().post(event)
    }

    /*
    * singleValue được dùng khi chỉ muốn truyền một giá trị duy nhất thông qua intent
    * bundleValue được dùng khi muốn truyền nhiều biến giá trị thông qua intent
    * ==> Tùy từng trường hợp, chỉ nên dùng singleValue hoặc bundleValue.
    * */
    fun goTo(destination: Class<*>, singleValue: String? = null, bundle: Bundle? = null, canBack: Boolean = true) {
        val intent = Intent(this, destination)
        if (bundle != null) {
            intent.putExtra(Constant.INTENT_KEY, bundle)
        }
        if (singleValue != null) {
            intent.putExtra(Constant.INTENT_KEY, singleValue)
        }
        startActivity(intent)
        this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        if (!canBack) finish()
    }

    fun notify(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}