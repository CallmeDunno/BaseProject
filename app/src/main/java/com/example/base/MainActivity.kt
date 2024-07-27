package com.example.base

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.base.data_local.UserViewModel
import com.example.base.databinding.ActivityMainBinding
import com.example.base.extensions.runDelay
import com.example.base.extensions.setAnimation
import com.example.base.extensions.setOnSingleClickListener
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutID: Int = R.layout.activity_main
    private lateinit var userViewModel: UserViewModel

    override fun initView() {
        super.initView()

        receiveEvent("aaa")

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.btnDelay.setOnSingleClickListener {
            runDelay(5000) {
//                val user = User(name = "John Doe", age = 30)
//                userViewModel.insert(user)
//                binding.tv.setAnimation(binding.tv, R.anim.exit_to_right, true)
            }

        }

        lifecycleScope.launch {
            userViewModel.getAllDataLiveData().observe(this@MainActivity) {
                Log.w("Dunno", "LiveData: ${it.size}")
            }

            userViewModel.getAllDataFlow().collect {
                Log.i("Dunno", "Flow: ${it.size}")
            }
        }
    }

    override fun handleEvent(event: Any?) {
        super.handleEvent(event)
        Log.e(TAG, event!!.toString())
    }

}