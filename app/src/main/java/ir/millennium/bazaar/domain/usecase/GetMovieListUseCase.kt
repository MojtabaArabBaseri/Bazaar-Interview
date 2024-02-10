package ir.millennium.bazaar.domain.usecase

import ir.millennium.bazaar.data.model.remote.MovieListModel
import ir.millennium.bazaar.data.repository.remote.RemoteRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetMovieListUseCase @Inject constructor(private val remoteRepository: RemoteRepositoryImpl) {

    open fun getArticles(
        headerMap: MutableMap<String, String>,
        params: MutableMap<String, Any>
    ): Flow<MovieListModel> {
        return remoteRepository.getMovieList(headerMap, params)
    }
}