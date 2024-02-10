package ir.millennium.bazaar.presentation.screens.splashScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.bazaar.core.utils.AuxiliaryFunctionsManager
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val auxiliaryFunctionsManager: AuxiliaryFunctionsManager
) : ViewModel() {
    var isLoadingData = mutableStateOf(false)
}