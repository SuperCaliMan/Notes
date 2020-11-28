package com.supercaliman.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.supercaliman.notification.domain.FcmRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var fcmRepo: FcmRepo


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(token: String) {
        // super.onNewToken(token)
        Log.d("TAG", "token $token")
        fcmRepo.sendRegistrationToServer(token)
    }
}