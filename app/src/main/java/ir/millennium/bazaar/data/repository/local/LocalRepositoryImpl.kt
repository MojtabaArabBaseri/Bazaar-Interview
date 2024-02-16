package ir.millennium.bazaar.data.repository.local

import ir.millennium.bazaar.data.dataSource.local.database.MovieDao
import ir.millennium.bazaar.data.mapper.mapToEntity
import ir.millennium.bazaar.data.mapper.mapToEntityList
import ir.millennium.bazaar.data.mapper.mapToUiModelList
import ir.millennium.bazaar.data.model.remote.MovieItem
import ir.millennium.bazaar.domain.repository.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var movieDao: MovieDao,
    private val applicationScope: CoroutineScope
) : LocalRepository {

    override suspend fun getMovieList(): List<MovieItem> {
        return movieDao.getMovieList().mapToUiModelList()
    }

    override suspend fun saveMovieToDatabase(movieItem: MovieItem) {
        return withContext(applicationScope.coroutineContext) {
            val x = movieDao.insertMovie(movieItem.mapToEntity())
        }
    }

    override suspend fun saveMovieListToDatabase(movieList: List<MovieItem>) {
        return withContext(applicationScope.coroutineContext) {
            movieDao.insertMovieList(movieList.mapToEntityList())
        }
    }

    override fun clearDatabase() {
        applicationScope.launch {
            movieDao.clear()
        }
    }
}