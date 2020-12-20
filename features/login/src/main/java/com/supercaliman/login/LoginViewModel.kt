package com.supercaliman.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.login.domain.AuthRepo
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val authApi: AuthRepo
) : BaseViewModel() {

    private val _userData = MutableLiveData<FirebaseUser>()
    val userData: LiveData<FirebaseUser> get() = _userData

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> get() = _error

    init {
        authApi.alreadyLogin()?.let {
            _userData.postValue(it)
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loader.postValue(true)
            try {
                val res = authApi.signIn(email, password)
                _userData.value = res
                _loader.postValue(false)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun createUser(username: String, email: String, password: String) {
        _loader.value = true
        viewModelScope.launch {
            _loader.postValue(true)
            try {
                val res = authApi.newUser(username, email, password)
                _userData.value = res
                _loader.value = false
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun logout() {
        authApi.logout()
    }


}