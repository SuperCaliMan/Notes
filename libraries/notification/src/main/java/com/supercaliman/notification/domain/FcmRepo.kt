package com.supercaliman.notification.domain

interface FcmRepo {
    suspend fun sendRegistrationToServer(token: String)
}