package com.qifan.thepsetest.app.base.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
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

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMenuId()?.run { setHasOptionsMenu(true) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        getMenuId()?.run { inflater.inflate(this, menu) }
    }

    abstract fun getMenuId(): Int?

    @LayoutRes
    abstract fun getLayoutId(): Int

}