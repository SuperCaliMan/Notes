package com.supercaliman.notification.domain

interface FcmRepo {
    fun sendRegistrationToServer(token: String)
}