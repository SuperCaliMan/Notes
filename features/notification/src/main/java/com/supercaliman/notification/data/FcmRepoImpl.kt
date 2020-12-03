package com.supercaliman.notification.data

import com.supercaliman.notification.api.FcmApi
import com.supercaliman.notification.domain.FcmRepo
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException

class FcmRepoImpl(private val api: FcmApi) : FcmRepo {

    override suspend fun sendRegistrationToServer(token: String) {
        try {
            api.sendToken(token)
        } catch (e: ConnectException) {
            Timber.e(e)
        } catch (e: HttpException) {
            Timber.e(e)
        }
    }
}