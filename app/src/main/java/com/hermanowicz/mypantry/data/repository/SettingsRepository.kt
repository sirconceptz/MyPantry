package com.hermanowicz.mypantry.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    val databaseMode: Flow<String?>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[DATABASE_MODE_KEY]
            }

    val scannerSoundMode: Flow<Boolean?>
        get() =
            context.dataStore.data.map { preferences ->
                preferences[SCANNER_SOUND_MODE_KEY]
            }

    suspend fun updateDatabaseMode(databaseMode: String) {
        context.dataStore.edit { preferences ->
            preferences[DATABASE_MODE_KEY] = databaseMode
        }
    }

    suspend fun updateScannerSoundMode(scannerSoundMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SCANNER_SOUND_MODE_KEY] = scannerSoundMode
        }
    }

    companion object {
        val DATABASE_MODE_KEY = stringPreferencesKey("database_mode")
        val SCANNER_SOUND_MODE_KEY = booleanPreferencesKey("scanner_sound_mode")
    }
}
