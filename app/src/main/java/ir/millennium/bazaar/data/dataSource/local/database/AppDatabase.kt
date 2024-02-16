package ir.millennium.bazaar.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.millennium.bazaar.domain.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}

