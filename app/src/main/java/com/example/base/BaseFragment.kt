package com.example.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    protected lateinit var binding: VB
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onObserve()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initAction()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun initView() {}

    open fun onObserve() {}

    open fun initAction() {}

    open fun initializeData(){}

    fun notify(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    /*
    * singleValue được dùng khi chỉ muốn truyền một giá trị duy nhất thông qua intent
    * bundleValue được dùng khi muốn truyền nhiều biến giá trị thông qua intent
    * ==> Tùy từng trường hợp, chỉ nên dùng singleValue hoặc bundleValue.
    * */
    fun goTo(destination: Class<*>, singleValue: String? = null, bundleValue: Bundle? = null, canBack: Boolean = true) {
        val intent = Intent(requireActivity(), destination)
        if (bundleValue != null) {
            intent.putExtra(Constant.INTENT_KEY, bundleValue)
        }
        if (singleValue != null) {
            intent.putExtra(Constant.INTENT_KEY, singleValue)
        }
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        if (!canBack) requireActivity().finish()
    }
}