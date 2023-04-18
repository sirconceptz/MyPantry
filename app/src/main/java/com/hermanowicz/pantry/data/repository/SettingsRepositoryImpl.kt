package com.hermanowicz.pantry.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hermanowicz.pantry.data.settings.AppSettings
import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.SizePrintedQRCodes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    override val appSettings: Flow<AppSettings>
        get() =
            context.dataStore.data.map { preferences ->
                val databaseMode = preferences[DATABASE_MODE_KEY] ?: DatabaseMode.LOCAL.name
                val cameraMode = preferences[CAMERA_MODE_KEY] ?: CameraMode.REAR.name
                val scannerSoundMode = preferences[SCANNER_SOUND_MODE_KEY] ?: true
                val sizePrintedQRCodes =
                    preferences[SIZE_PRINTED_QR_CODES_KEY] ?: SizePrintedQRCodes.BIG.name
                val daysToNotifyBeforeExpiration =
                    preferences[DAYS_TO_NOTIFY_BEFORE_EXPIRATION] ?: 3
                val emailForNotifications = preferences[EMAIL_FOR_NOTIFICATIONS_KEY] ?: ""
                val pushNotifications = preferences[PUSH_NOTIFICATIONS_KEY] ?: true
                val emailNotifications = preferences[EMAIL_NOTIFICATIONS_KEY] ?: false

                AppSettings(
                    databaseMode = databaseMode,
                    cameraMode = cameraMode,
                    scannerSound = scannerSoundMode,
                    daysToNotifyBeforeExpiration = daysToNotifyBeforeExpiration.toFloat(),
                    emailForNotifications = emailForNotifications,
                    sizePrintedQRCodes = sizePrintedQRCodes,
                    pushNotifications = pushNotifications,
                    emailNotifications = emailNotifications
                )
            }


    override suspend fun updateAppSettings(appSettings: AppSettings) {
        context.dataStore.edit { preferences ->
            preferences[DATABASE_MODE_KEY] = appSettings.databaseMode
            preferences[CAMERA_MODE_KEY] = appSettings.cameraMode
            preferences[SCANNER_SOUND_MODE_KEY] = appSettings.scannerSound
            preferences[SIZE_PRINTED_QR_CODES_KEY] = appSettings.sizePrintedQRCodes
            preferences[DAYS_TO_NOTIFY_BEFORE_EXPIRATION] =
                appSettings.daysToNotifyBeforeExpiration.toInt()
            preferences[EMAIL_FOR_NOTIFICATIONS_KEY] = appSettings.emailForNotifications
            preferences[EMAIL_NOTIFICATIONS_KEY] = appSettings.emailNotifications
            preferences[PUSH_NOTIFICATIONS_KEY] = appSettings.pushNotifications
        }
    }

    override val databaseMode: Flow<DatabaseMode> = run {
        context.dataStore.data.map { preferences ->
            enumValueOf(preferences[DATABASE_MODE_KEY] ?: DatabaseMode.LOCAL.name)
        }
    }

    companion object {
        val DATABASE_MODE_KEY = stringPreferencesKey("database_mode")
        val CAMERA_MODE_KEY = stringPreferencesKey("camera_mode")
        val EMAIL_FOR_NOTIFICATIONS_KEY = stringPreferencesKey("email_for_notifications")
        val SCANNER_SOUND_MODE_KEY = booleanPreferencesKey("scanner_sound_mode")
        val DAYS_TO_NOTIFY_BEFORE_EXPIRATION = intPreferencesKey("days_to_notify_before_expiration")
        val SIZE_PRINTED_QR_CODES_KEY = stringPreferencesKey("size_printed_qr_codes")
        val PUSH_NOTIFICATIONS_KEY = booleanPreferencesKey("push_notifications")
        val EMAIL_NOTIFICATIONS_KEY = booleanPreferencesKey("email_notifications")
    }
}
