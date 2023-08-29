package com.hermanowicz.pantry.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.hermanowicz.pantry.MainActivity
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.domain.settings.FetchDaysBeforeNotificationUseCase
import com.hermanowicz.pantry.domain.settings.FetchEmailAddressForNotificationsUseCase
import com.hermanowicz.pantry.domain.settings.FetchIsEmailNotificationsEnabledUseCase
import com.hermanowicz.pantry.domain.settings.FetchIsPushNotificationsEnabledUseCase
import com.hermanowicz.pantry.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

/**
 * <h1>NotificationBroadcastReceiver</h1>
 * Broadcast receiver for notifications. Sending details needed to prepare notification.
 *
 * @author Mateusz Hermanowicz
 */
@AndroidEntryPoint
class NotificationBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var fetchDaysBeforeNotificationUseCase: FetchDaysBeforeNotificationUseCase

    @Inject
    lateinit var fetchIsPushNotificationsEnabledUseCase: FetchIsPushNotificationsEnabledUseCase

    @Inject
    lateinit var fetchIsEmailNotificationsEnabledUseCase: FetchIsEmailNotificationsEnabledUseCase

    @Inject
    lateinit var fetchEmailAddressForNotificationsUseCase: FetchEmailAddressForNotificationsUseCase

    private val job by lazy { SupervisorJob() }
    private val scope by lazy { CoroutineScope(Dispatchers.Main.immediate + job) }

    override fun onReceive(context: Context, intent: Intent?) {
        Timber.d("Broadcast receiver notification: Received Notification")
        scope.launch {
            val productName = intent?.getStringExtra("product_name") ?: ""
            val productID = intent?.getIntExtra("product_id", 0) ?: 0

            val daysToNotification = fetchDaysBeforeNotificationUseCase().first()
            val isPushNotificationsEnabled = fetchIsPushNotificationsEnabledUseCase().first()
            val isEmailNotificationsEnabled = fetchIsEmailNotificationsEnabledUseCase().first()
            val emailAddressForNotifications = fetchEmailAddressForNotificationsUseCase().first()

            if (isPushNotificationsEnabled) {
                createPushNotification(context, productID, productName, daysToNotification)
            }
            if (isEmailNotificationsEnabled) {
                createEmailNotification(
                    context,
                    emailAddressForNotifications,
                    productName,
                    daysToNotification
                )
            }
        }
    }

    private fun createStatement(
        context: Context,
        productName: String,
        daysToNotification: Int
    ): String {
        var statement: String = context.getString(R.string.notification_statement)
        statement = statement.replace(Constants.DAYS_TAG, daysToNotification.toString())
        statement = statement.replace(Constants.PRODUCT_NAME_TAG, productName)
        return statement
    }

    fun createEmailNotification(
        context: Context,
        emailAddressForNotifications: String,
        productName: String,
        daysToNotification: Int
    ) {
        val params = HashMap<String, String>()
        params["to_email_address"] = emailAddressForNotifications
        params["subject"] = context.getString(R.string.notification_title)
        params["message"] = createStatement(context, productName, daysToNotification)
        val url = Constants.URL_MAIL_API + Constants.API_MAIL_FILE
        val requestJson = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject((params as Map<*, *>)),
            { }
        ) { error ->
            Timber.e(
                "Error - Json parsing notification " + error.message
            )
        }
        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(requestJson)
    }

    fun createPushNotification(
        context: Context,
        productID: Int,
        productName: String,
        daysToNotification: Int
    ) {
        val channelId = "my_channel_$productID"
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val name: CharSequence = context.getString(R.string.expiration_date)
        val description = "Products expiration dates notification channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.setShowBadge(false)
        notificationManager.createNotificationChannel(channel)
        val notificationStatement = createStatement(context, productName, daysToNotification)
        val builder = NotificationCompat.Builder(context, channelId)
        builder.setContentTitle(context.getString(R.string.notification_title))
        builder.setContentText(notificationStatement)
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(notificationStatement))
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
        builder.setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.mipmap.ic_launcher_round
            )
        )
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        builder.setLights(ContextCompat.getColor(context, R.color.color_primary), 500, 1000)
        builder.setAutoCancel(true)
        val notifyIntent = Intent(context, MainActivity::class.java)
        notifyIntent.flags = (
            Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)
        val notificationCompat = builder.build()
        notificationManager.notify(productID, notificationCompat)
    }
}
