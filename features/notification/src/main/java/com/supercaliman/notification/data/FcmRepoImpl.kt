package com.supercaliman.notification.data

import com.supercaliman.notification.api.FcmApi
import com.supercaliman.notification.domain.FcmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException

class FcmRepoImpl(private val api: FcmApi) : FcmRepo {

    override fun sendRegistrationToServer(token: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                api.sendToken(token)
            } catch (e: ConnectException) {
                Timber.e(e)
            }
        }
    }
}