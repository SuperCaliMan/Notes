package com.supercaliman.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supercaliman.core.base.BaseViewModel
import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.login.domain.AuthRepo
import com.supercaliman.login.domain.UiUser
import com.supercaliman.login.domain.UiUserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val authApi: AuthRepo,
    private val localDataSource: LocalRepository
) : BaseViewModel() {

    private val _userData = MutableLiveData<UiUser>()
    val userData: LiveData<UiUser> get() = _userData

    private val mapper = UiUserMapper()


    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loader.postValue(true)
            try {
                val res = authApi.signIn(email, password)
                res?.let {
                    localDataSource.saveUser(it)
                    _userData.postValue(mapper.toUiModel(it))
                }
                _loader.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e)
            }
        }
    }

    fun createUser(username: String, email: String, password: String) {
        _loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = authApi.newUser(username, email, password)
                res?.let {
                    localDataSource.saveUser(it)
                    _userData.postValue(mapper.toUiModel(it))
                }
                _loader.postValue(false)
            } catch (e: Exception) {
                _loader.postValue(false)
                _error.postValue(e)
            }
        }
    }

    fun sendVerificationEmail() {
        _loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authApi.sendVerificationEmail()
                _loader.postValue(false)
            } catch (e: java.lang.Exception) {
                _loader.postValue(false)
                _error.postValue(e)
            }
        }
    }

    fun logout() {
        localDataSource.deleteUser()
        authApi.logout()
    }


}