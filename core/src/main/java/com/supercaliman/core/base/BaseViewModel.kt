package com.supercaliman.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supercaliman.core.domain.SingleLiveEvent


open class BaseViewModel : ViewModel() {

    protected var _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    protected var _error = SingleLiveEvent<Exception>()
    val error: LiveData<Exception>
        get() = _error

}