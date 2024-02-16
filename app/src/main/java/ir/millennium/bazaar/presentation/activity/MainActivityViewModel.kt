package ir.millennium.bazaar.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.bazaar.core.theme.enums.TypeTheme
import ir.millennium.bazaar.data.dataSource.local.preferencesDataStoreManager.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val statusThemeFlow = userPreferencesRepository.statusTheme
    private var _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

    fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                viewModelScope.launch {
                    userPreferencesRepository.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                }
                _typeTheme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                viewModelScope.launch {
                    userPreferencesRepository.setStatusTheme(TypeTheme.DARK.typeTheme)
                }
                _typeTheme.value = TypeTheme.DARK.typeTheme
            }
        }
    }
}