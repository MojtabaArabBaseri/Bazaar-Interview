package ir.millennium.bazaar.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.millennium.bazaar.data.model.local.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}

