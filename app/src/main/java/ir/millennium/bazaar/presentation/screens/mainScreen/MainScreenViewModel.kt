package ir.millennium.bazaar.presentation.screens.mainScreen

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.bazaar.core.utils.AuxiliaryFunctionsManager
import ir.millennium.bazaar.data.dataSource.remote.UiState
import ir.millennium.bazaar.data.model.remote.MovieItem
import ir.millennium.bazaar.data.repository.local.LocalRepositoryImpl
import ir.millennium.bazaar.domain.usecase.GetMovieListUseCase
import ir.millennium.bazaar.presentation.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val localRepositoryImpl: LocalRepositoryImpl,
    private val auxiliaryFunctionsManager: AuxiliaryFunctionsManager
) : ViewModel() {

    val stateLazyColumn = LazyGridState()

    private val _isShowLoadingData = MutableStateFlow(false)
    val isShowLoadingData: StateFlow<Boolean> = _isShowLoadingData

    private val _uiState = MutableStateFlow<UiState>(UiState.Initialization)
    val uiState: StateFlow<UiState> = _uiState

    var movieList = mutableStateListOf<MovieItem>()

    private var currentPage = 1

    private val headerMap = mutableMapOf<String, String>()
    private val params = mutableMapOf<String, Any>()

    init {
        headerMap["Authorization"] = Constants.BEARER_TOKEN
        params["language"] = "en-US"
        params["page"] = currentPage
        getMovieList()
    }

    private fun getMovieList() {
        if (auxiliaryFunctionsManager.isNetworkConnected()) {
            getMovieListFromRemote()
        } else {
            getMovieListFromDatabase()
        }
    }

    private fun getMovieListFromRemote() {

        params.replace("page", currentPage)

        getMovieListUseCase.getArticles(headerMap, params)
            .flowOn(Dispatchers.IO)
            .map { movieList ->
                movieList.results?.let {
                    this.movieList.addAll(movieList.results)
                    if (currentPage == 1) {
                        localRepositoryImpl.clearDatabase()
                    }
                    localRepositoryImpl.saveMovieListToDatabase(movieList.results)
                }
                _uiState.value = UiState.Success(this.movieList)
            }
            .onStart {
                _uiState.value = UiState.Loading
            }
            .catch { throwable ->
                _uiState.value = UiState.Error(throwable)
            }.launchIn(viewModelScope)

    }

    private fun getMovieListFromDatabase() {
        viewModelScope.launch {
            movieList.addAll(localRepositoryImpl.getMovieList())
        }
    }

    fun refresh() {
        currentPage = 1
        movieList.clear()
        getMovieList()
    }

    fun getNextPage() {
        currentPage++
        if (auxiliaryFunctionsManager.isNetworkConnected()) {
            getMovieListFromRemote()
        }
    }

    fun retryRequest() {
        if (auxiliaryFunctionsManager.isNetworkConnected()) {
            getMovieListFromRemote()
        }
    }

    fun isShowLoadingData(isShow: Boolean) {
        _isShowLoadingData.value = isShow
    }
}
