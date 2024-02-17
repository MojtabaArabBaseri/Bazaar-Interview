package ir.millennium.bazaar.data.dataSource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ir.millennium.bazaar.data.dataSource.local.database.AppDatabase
import ir.millennium.bazaar.data.mapper.mapToMovieEntity
import ir.millennium.bazaar.domain.entity.MovieEntity
import ir.millennium.bazaar.presentation.utils.Constants
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id!! / state.config.pageSize) + 1
                    }
                }
            }
            val headerMap = mutableMapOf<String, String>()
            headerMap["Authorization"] = Constants.BEARER_TOKEN

            val movieListModel = apiService.getMovieList(
                headerMap = headerMap,
                page = loadKey,
            )

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.movieDao().clearAll()
                }
                val movieEntityList = movieListModel.results.map { it.mapToMovieEntity() }
                appDatabase.movieDao().upsertAll(movieEntityList)
            }

            MediatorResult.Success(
                endOfPaginationReached = movieListModel.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}