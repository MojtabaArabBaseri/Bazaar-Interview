package ir.millennium.bazaar.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val title: String?,
    val poster: String?
)