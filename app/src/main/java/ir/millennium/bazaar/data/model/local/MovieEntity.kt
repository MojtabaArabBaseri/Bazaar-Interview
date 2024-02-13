package ir.millennium.bazaar.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val title: String?,
    val poster: String?
)