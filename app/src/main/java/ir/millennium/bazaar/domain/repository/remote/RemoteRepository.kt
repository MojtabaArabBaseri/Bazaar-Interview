package ir.millennium.bazaar.domain.repository.remote

import ir.millennium.bazaar.data.model.remote.MovieListModel
import kotlinx.coroutines.flow.Flow


interface RemoteRepository {
    fun getMovieList(
        headerMap: MutableMap<String, String>,
        params: MutableMap<String, Any>
    ): Flow<MovieListModel>
}