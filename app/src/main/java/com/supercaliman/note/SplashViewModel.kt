package com.supercaliman.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.dto.User
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val localDataSource: LocalRepository
) : BaseViewModel() {

    private val _alreadyLogin = MutableLiveData<User?>()
    val alreadyLogin: LiveData<User?> get() = _alreadyLogin


    init {
        userAlreadyLogin()
    }


    private fun userAlreadyLogin() {
        viewModelScope.launch {
            _alreadyLogin.postValue(localDataSource.getSavedUser())
        }
    }


}