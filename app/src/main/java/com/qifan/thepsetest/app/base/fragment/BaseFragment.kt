package com.qifan.thepsetest.app.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.qifan.thepsetest.extension.inflateLayout

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflateLayout(getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

}