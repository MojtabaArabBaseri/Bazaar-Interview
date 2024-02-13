package ir.millennium.bazaar.data.dataSource.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.millennium.bazaar.data.model.local.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY id ASC")
    suspend fun getMovieList(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieEntity: List<MovieEntity>)

    @Query("DELETE FROM movie")
    suspend fun clear()
}