package com.supercaliman.notification


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.supercaliman.notification.domain.FcmRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


@AndroidEntryPoint
class NotificationService : FirebaseMessagingService(), CoroutineScope {

    private var notificationId = 1

    companion object {
        private const val CHANNEL = "PROVA"
    }

    @Inject
    lateinit var fcmRepo: FcmRepo

    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("NOTIFICATION data: ${remoteMessage.data}")
            createNotificationChannel()
            val builder = NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle(applicationContext.applicationInfo.nonLocalizedLabel)
                .setContentText(remoteMessage.data["message"].toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }
        }


    }


    override fun onNewToken(token: String) {
        Timber.d("TOKEN: $token")
        launch(Dispatchers.IO) {
            fcmRepo.sendRegistrationToServer(token)
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.common_google_play_services_notification_channel_name)
            val descriptionText = "CHANNEL DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}