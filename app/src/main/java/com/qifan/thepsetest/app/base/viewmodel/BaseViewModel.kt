package com.qifan.thepsetest.app.base.viewmodel

import androidx.lifecycle.ViewModel
import com.qifan.thepsetest.app.base.ReactiveBehavior
import com.qifan.thepsetest.app.base.ReactiveBehaviorDelegate

abstract class BaseViewModel : ViewModel(),
    ReactiveBehavior by ReactiveBehaviorDelegate() {

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}