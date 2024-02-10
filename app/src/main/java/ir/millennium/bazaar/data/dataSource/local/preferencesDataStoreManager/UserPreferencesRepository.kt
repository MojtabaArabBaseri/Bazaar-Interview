package ir.millennium.bazaar.data.dataSource.local.preferencesDataStoreManager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.millennium.bazaar.domain.entity.TypeTheme
import ir.millennium.bazaar.presentation.utils.Constants.USER_PREFERENCES_REPOSITORY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_REPOSITORY)

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext appContext: Context) {

    private val TAG = USER_PREFERENCES_REPOSITORY

    private val settingsDataStore = appContext.dataStore

    private object PreferencesKeys {
        val TYPE_THEME = intPreferencesKey("type_theme")
    }


    suspend fun setStatusTheme(isLightThemeActive: Int) {
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.TYPE_THEME] = isLightThemeActive
        }
    }

    val statusTheme = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.TYPE_THEME] ?: TypeTheme.LIGHT.typeTheme
        }
}