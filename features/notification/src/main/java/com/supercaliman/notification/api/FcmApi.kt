package com.supercaliman.notification.api

import retrofit2.http.PUT
import retrofit2.http.Path

public interface FcmApi {

    @PUT("fcm/v1/registerToken/{token}")
    suspend fun sendToken(@Path("token") token: String)
}