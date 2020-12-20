package com.supercaliman.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

    protected var _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

}