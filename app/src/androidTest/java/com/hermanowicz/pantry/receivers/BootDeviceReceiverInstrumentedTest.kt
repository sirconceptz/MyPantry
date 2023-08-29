package com.hermanowicz.pantry.receivers

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hermanowicz.pantry.MainActivity
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.settings.ReCreateNotificationsForAllProductsUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BootDeviceReceiverInstrumentedTest {

    @get:Rule
    val activityRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testBroadcastReceived() {
        val context = activityRule.activity.baseContext
        val fakeDatabaseMode = DatabaseMode.LOCAL

        val mockReCreateNotificationsUseCase: ReCreateNotificationsForAllProductsUseCase = mockk()
        val mockObserveDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()

        coEvery { mockObserveDatabaseModeUseCase() } returns flowOf(fakeDatabaseMode)
        coEvery { mockReCreateNotificationsUseCase(any(), any()) } returns Unit

        val receiver = BootDeviceReceiver().apply {
            reCreateNotificationsForAllProductsUseCase = mockReCreateNotificationsUseCase
            observeDatabaseModeUseCase = mockObserveDatabaseModeUseCase
        }

        val intent = Intent(context, BootDeviceReceiver::class.java)

        receiver.onReceive(context, intent)
    }
}
