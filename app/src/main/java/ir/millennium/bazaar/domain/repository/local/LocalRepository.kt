package ir.millennium.bazaar.domain.repository.local

import ir.millennium.bazaar.data.model.remote.MovieItem

interface LocalRepository {

    suspend fun getMovieList(): List<MovieItem>

    suspend fun saveMovieToDatabase(movieItem: MovieItem)

    suspend fun saveMovieListToDatabase(movieItem: List<MovieItem>)

    fun clearDatabase()

}