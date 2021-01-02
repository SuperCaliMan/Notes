package com.supercaliman.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.supercaliman.core.domain.SingleLiveEvent
import com.supercaliman.core.domain.dto.User


open class BaseViewModel : ViewModel() {

    protected var userLog: User? = null

    fun setUser(user: User) {
        userLog = user
    }

    protected var _loader = MediatorLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader

    protected var _error = SingleLiveEvent<Exception>()
    val error: LiveData<Exception>
        get() = _error

}