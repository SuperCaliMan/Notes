package com.supercaliman.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.login.domain.AuthRepo
import com.supercaliman.login.domain.UiUser
import com.supercaliman.login.domain.UiUserMapper
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val authApi: AuthRepo
) : BaseViewModel() {

    private val _userData = MutableLiveData<UiUser>()
    val userData: LiveData<UiUser> get() = _userData

    private val mapper = UiUserMapper()


    init {
        authApi.alreadyLogin()?.let {
            _userData.postValue(mapper.toUiModel(it))
        }
    }

    fun checkUserSignIn(): UiUser? = authApi.alreadyLogin()?.let { mapper.toUiModel(it) }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loader.postValue(true)
            try {
                val res = authApi.signIn(email, password)
                _userData.value = res?.let { mapper.toUiModel(it) }
                _loader.postValue(false)
            } catch (e: Exception) {
                _error.value = e
            }
        }
    }

    fun createUser(username: String, email: String, password: String) {
        _loader.value = true
        viewModelScope.launch {
            try {
                val res = authApi.newUser(username, email, password)
                res?.let {
                    _userData.value = mapper.toUiModel(it)
                }
                _loader.value = false
            } catch (e: Exception) {
                _loader.value = false
                _error.value = e
            }
        }
    }

    fun sendVerificationEmail() {
        _loader.value = true
        viewModelScope.launch {
            try {
                authApi.sendVerificationEmail()
                _loader.value = false
            } catch (e: java.lang.Exception) {
                _loader.value = false
                _error.value = e
            }
        }
    }

    fun logout() {
        authApi.logout()
    }


}