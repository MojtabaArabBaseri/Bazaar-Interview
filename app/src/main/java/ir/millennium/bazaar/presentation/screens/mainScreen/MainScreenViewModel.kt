package ir.millennium.bazaar.presentation.screens.mainScreen

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.bazaar.data.mapper.mapToMovieItem
import ir.millennium.bazaar.domain.usecase.GetMovieListUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    val stateLazyColumn = LazyGridState()

    val moviePagingFlow = getMovieListUseCase.invoke()
        .map { pagingData ->
            pagingData.map { it.mapToMovieItem() }
        }
        .cachedIn(viewModelScope)
}