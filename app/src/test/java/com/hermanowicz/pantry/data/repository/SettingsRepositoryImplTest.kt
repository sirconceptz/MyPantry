package com.hermanowicz.pantry.data.repository

import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.time.Duration.Companion.seconds

class SettingsRepositoryImplTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private lateinit var notificationManagerCompat: NotificationManagerCompat
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var settingsRepository: SettingsRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    private val appSettings = AppSettings(
        databaseMode = "LOCAL",
        cameraMode = "REAR",
        daysToNotifyBeforeExpiration = 3f,
        emailForNotifications = "",
        qrCodeSize = "BIG",
        emailNotifications = false,
        pushNotifications = true,
        pushNotificationsChanged = false
    )

    @Before
    fun setup() {
        notificationManagerCompat = mockk(relaxed = true)
        coEvery { notificationManagerCompat.areNotificationsEnabled() } returns true
        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { tmpFolder.newFile("user.preferences_pb") }
        )

        settingsRepository = SettingsRepositoryImpl(notificationManagerCompat, dataStore)
    }

    @Test
    fun `test updateAppSettings updates preferences`() = runTest(testDispatcher, 60.seconds) {
        settingsRepository.updateAppSettings(appSettings)
        delay(100)

        val result = settingsRepository.appSettings.first()
        assertEquals(appSettings, result)
    }

    @Test
    fun `test isPushNotificationsEnabled returns correct value`() =
        runTest(testDispatcher, 60.seconds) {
            val result = settingsRepository.isPushNotificationsEnabled.first()
            delay(100)

            assertEquals(true, result)
        }

    @Test
    fun `test databaseMode returns correct value`() = runTest(testDispatcher, 60.seconds) {
        settingsRepository.updateAppSettings(appSettings.copy(databaseMode = DatabaseMode.LOCAL.name))
        delay(100)
        val result = settingsRepository.databaseMode.first()

        assertEquals(DatabaseMode.LOCAL.name, result)
    }

    @Test
    fun `test qrCodeSize returns correct value`() = runTest(testDispatcher, 60.seconds) {
        settingsRepository.updateAppSettings(appSettings.copy(qrCodeSize = QrCodeSize.SMALL.name))
        delay(100)
        val result = settingsRepository.qrCodeSize.first()

        assertEquals("SMALL", result)
    }

    @Test
    fun `test daysBeforeNotification returns correct value`() =
        runTest(testDispatcher, 60.seconds) {
            settingsRepository.updateAppSettings(appSettings.copy(daysToNotifyBeforeExpiration = 5F))
            delay(100)
            val result = settingsRepository.daysBeforeNotification.first()

            assertEquals(5, result)
        }

    @Test
    fun `test isEmailNotificationsEnabled returns correct value`() =
        runTest(testDispatcher, 60.seconds) {
            settingsRepository.updateAppSettings(appSettings.copy(emailNotifications = true))
            delay(100)
            val result = settingsRepository.isEmailNotificationsEnabled.first()

            assertEquals(true, result)
        }

    @Test
    fun `test emailAddressForNotifications returns correct value`() =
        runTest(testDispatcher, 60.seconds) {
            val expectedEmailAddress = "test@example.com"
            settingsRepository.updateAppSettings(appSettings.copy(emailForNotifications = expectedEmailAddress))
            delay(100)
            val result = settingsRepository.emailAddressForNotifications.first()

            assertEquals(expectedEmailAddress, result)
        }

    @Test
    fun `test scanCameraMode returns correct value`() = runTest(testDispatcher, 60.seconds) {
        settingsRepository.updateAppSettings(appSettings.copy(cameraMode = CameraMode.FRONT.name))
        val result = settingsRepository.scanCameraMode.first()
        delay(100)

        assertEquals(CameraMode.FRONT.name, result)
    }
}
