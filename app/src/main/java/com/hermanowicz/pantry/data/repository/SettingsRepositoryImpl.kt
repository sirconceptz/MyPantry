package com.hermanowicz.pantry.data.repository

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val appSettings: Flow<AppSettings>
        get() =
            isPushNotificationsEnabled.flatMapLatest {
                context.dataStore.data
            }
                .catch { e ->
                    if (e is IOException) {
                        Timber.e("PREFERENCE", "Error reading preferences", e)
                        emit(emptyPreferences())
                    } else {
                        throw e
                    }
                }.map { preferences ->
                    val databaseMode = preferences[DATABASE_MODE_KEY] ?: DatabaseMode.LOCAL.name
                    val cameraMode = preferences[CAMERA_MODE_KEY] ?: CameraMode.REAR.name
                    val qrCodeSize =
                        preferences[SIZE_PRINTED_QR_CODES_KEY] ?: QrCodeSize.BIG.name
                    val daysToNotifyBeforeExpiration =
                        preferences[DAYS_TO_NOTIFY_BEFORE_EXPIRATION] ?: 3
                    val emailForNotifications =
                        preferences[EMAIL_ADDRESS_FOR_NOTIFICATIONS_KEY] ?: ""
                    val emailNotifications = preferences[EMAIL_NOTIFICATIONS_KEY] ?: false
                    val pushNotificationsChanged =
                        preferences[PUSH_NOTIFICATIONS_CHANGED_KEY] ?: false

                    AppSettings(
                        databaseMode = databaseMode,
                        cameraMode = cameraMode,
                        daysToNotifyBeforeExpiration = daysToNotifyBeforeExpiration.toFloat(),
                        emailForNotifications = emailForNotifications,
                        qrCodeSize = qrCodeSize,
                        emailNotifications = emailNotifications,
                        pushNotifications = isNotificationsEnabled(),
                        pushNotificationsChanged = pushNotificationsChanged
                    )
                }.distinctUntilChanged()

    override suspend fun updateAppSettings(appSettings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[DATABASE_MODE_KEY] = appSettings.databaseMode
            preferences[CAMERA_MODE_KEY] = appSettings.cameraMode
            preferences[SIZE_PRINTED_QR_CODES_KEY] = appSettings.qrCodeSize
            preferences[DAYS_TO_NOTIFY_BEFORE_EXPIRATION] =
                appSettings.daysToNotifyBeforeExpiration.toInt()
            preferences[EMAIL_ADDRESS_FOR_NOTIFICATIONS_KEY] = appSettings.emailForNotifications
            preferences[EMAIL_NOTIFICATIONS_KEY] = appSettings.emailNotifications
            preferences[PUSH_NOTIFICATIONS_CHANGED_KEY] = appSettings.pushNotificationsChanged
            _isPushNotificationsEnabled.update {
                isNotificationsEnabled()
            }
        }
    }

    private val _isPushNotificationsEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(isNotificationsEnabled())
    override val isPushNotificationsEnabled: StateFlow<Boolean> =
        _isPushNotificationsEnabled.asStateFlow()

    override val databaseMode: Flow<String>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[DATABASE_MODE_KEY] ?: DatabaseMode.LOCAL.name
            }.distinctUntilChanged()

    override val qrCodeSize: Flow<String>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[SIZE_PRINTED_QR_CODES_KEY] ?: QrCodeSize.BIG.name
            }.distinctUntilChanged()

    override val daysBeforeNotification: Flow<Int>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[DAYS_TO_NOTIFY_BEFORE_EXPIRATION] ?: 3
            }.distinctUntilChanged()

    override val isEmailNotificationsEnabled: Flow<Boolean>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[EMAIL_NOTIFICATIONS_KEY] ?: false
            }.distinctUntilChanged()

    override val emailAddressForNotifications: Flow<String>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[EMAIL_ADDRESS_FOR_NOTIFICATIONS_KEY] ?: ""
            }.distinctUntilChanged()

    override val scanCameraMode: Flow<String>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[CAMERA_MODE_KEY] ?: CameraMode.REAR.name
            }.distinctUntilChanged()

    private fun isNotificationsEnabled(): Boolean {
        return notificationManagerCompat.areNotificationsEnabled()
    }

    companion object {
        val DATABASE_MODE_KEY = stringPreferencesKey("database_mode")
        val CAMERA_MODE_KEY = stringPreferencesKey("camera_mode")
        val EMAIL_ADDRESS_FOR_NOTIFICATIONS_KEY = stringPreferencesKey("email_for_notifications")
        val DAYS_TO_NOTIFY_BEFORE_EXPIRATION = intPreferencesKey("days_to_notify_before_expiration")
        val SIZE_PRINTED_QR_CODES_KEY = stringPreferencesKey("size_printed_qr_codes")
        val EMAIL_NOTIFICATIONS_KEY = booleanPreferencesKey("email_notifications")
        val PUSH_NOTIFICATIONS_CHANGED_KEY = booleanPreferencesKey("push_notifications_changed")
    }
}
