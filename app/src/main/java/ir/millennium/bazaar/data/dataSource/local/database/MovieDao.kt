package ir.millennium.bazaar.data.dataSource.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.millennium.bazaar.domain.entity.MovieEntity

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(beers: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun clearAll()
}