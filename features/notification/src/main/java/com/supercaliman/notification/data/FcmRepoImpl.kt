package com.supercaliman.notification.data

import com.supercaliman.notification.api.FcmApi
import com.supercaliman.notification.domain.FcmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FcmRepoImpl(private val api: FcmApi) : FcmRepo {

    override fun sendRegistrationToServer(token: String) {
        GlobalScope.launch(Dispatchers.IO) {
            api.sendToken(token)
        }
    }
}