package com.example.composetest.login.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.util.Constants.PREFERENCES_NAME
import com.example.composetest.login.util.Constants.PREFERENCES_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val token = stringPreferencesKey(name = PREFERENCES_TOKEN)
    }

    private val dataStore = context.dataStore

    override suspend fun saveToken(token: String?) {
        token?.let{
            dataStore.edit { preferences ->
                preferences[PreferencesKey.token] = token
            }
        }
    }

    override fun readToken(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.token]
            }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.token)
        }
    }
}