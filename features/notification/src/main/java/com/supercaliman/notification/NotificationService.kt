package com.supercaliman.notification


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.supercaliman.notification.domain.FcmRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var fcmRepo: FcmRepo


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }

    override fun onNewToken(token: String) {
        Timber.d("TOKEN: $token")
        GlobalScope.launch(Dispatchers.IO) {
            fcmRepo.sendRegistrationToServer(token)
        }
    }
}