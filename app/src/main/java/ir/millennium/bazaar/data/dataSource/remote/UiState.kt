package ir.millennium.bazaar.data.dataSource.remote

sealed class UiState {
    object Initialization : UiState()
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val throwable: Throwable) : UiState()
}