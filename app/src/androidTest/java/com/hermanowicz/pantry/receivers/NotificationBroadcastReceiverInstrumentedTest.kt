package com.hermanowicz.pantry.receivers

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.hermanowicz.pantry.MainActivity
import com.hermanowicz.pantry.domain.settings.FetchDaysBeforeNotificationUseCase
import com.hermanowicz.pantry.domain.settings.FetchEmailAddressForNotificationsUseCase
import com.hermanowicz.pantry.domain.settings.FetchIsEmailNotificationsEnabledUseCase
import com.hermanowicz.pantry.domain.settings.FetchIsPushNotificationsEnabledUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NotificationBroadcastReceiverInstrumentedTest {

    @get:Rule
    val activityRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    private val mockFetchDaysBeforeNotificationUseCase: FetchDaysBeforeNotificationUseCase = mockk(relaxed = true)
    private val mockFetchIsPushNotificationsEnabledUseCase: FetchIsPushNotificationsEnabledUseCase = mockk(relaxed = true)
    private val mockFetchIsEmailNotificationsEnabledUseCase: FetchIsEmailNotificationsEnabledUseCase = mockk(relaxed = true)
    private val mockFetchEmailAddressForNotificationsUseCase: FetchEmailAddressForNotificationsUseCase = mockk(relaxed = true)

    @Test
    fun testNotificationBroadcastReceived() {
        val appContext = activityRule.activity.applicationContext
        val intent = Intent(appContext, NotificationBroadcastReceiver::class.java)
        intent.putExtra("product_name", "Test Product")
        intent.putExtra("product_id", 123)

        coEvery { mockFetchDaysBeforeNotificationUseCase() } returns flowOf(5)
        coEvery { mockFetchIsPushNotificationsEnabledUseCase() } returns flowOf(true)
        coEvery { mockFetchIsEmailNotificationsEnabledUseCase() } returns flowOf(true)
        coEvery { mockFetchEmailAddressForNotificationsUseCase() } returns flowOf("test@example.com")

        val receiver = NotificationBroadcastReceiver().apply {
            fetchDaysBeforeNotificationUseCase = mockFetchDaysBeforeNotificationUseCase
            fetchIsPushNotificationsEnabledUseCase = mockFetchIsPushNotificationsEnabledUseCase
            fetchIsEmailNotificationsEnabledUseCase = mockFetchIsEmailNotificationsEnabledUseCase
            fetchEmailAddressForNotificationsUseCase = mockFetchEmailAddressForNotificationsUseCase
        }

        receiver.onReceive(appContext, intent)
    }

    @Test
    fun testPushNotificationCreated() {
        val appContext = activityRule.activity.applicationContext
        val intent = Intent(appContext, NotificationBroadcastReceiver::class.java)

        coEvery { mockFetchDaysBeforeNotificationUseCase() } returns flowOf(5)
        coEvery { mockFetchIsPushNotificationsEnabledUseCase() } returns flowOf(true)
        coEvery { mockFetchIsEmailNotificationsEnabledUseCase() } returns flowOf(true)

        val receiver = NotificationBroadcastReceiver()
        val manager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        receiver.onReceive(appContext, intent)
        receiver.createPushNotification(appContext, 123, "Product 123", 0)

        activityRule.waitUntil(timeoutMillis = 10000) { manager.activeNotifications.isNotEmpty() }

        val notificationsDisplayed = manager.activeNotifications.isNotEmpty()

        assert(notificationsDisplayed)
    }
}
