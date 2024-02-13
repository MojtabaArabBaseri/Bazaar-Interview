package ir.millennium.bazaar.data.mapper

import ir.millennium.bazaar.data.model.local.MovieEntity
import ir.millennium.bazaar.data.model.remote.MovieItem

fun MovieEntity.mapToUiModel() =
    MovieItem(title = this.title, posterPath = this.poster)

fun List<MovieEntity>.mapToUiModelList() = map {
    it.mapToUiModel()
}

fun MovieItem.mapToEntity() =
    MovieEntity(title = this.title, poster = this.posterPath)

fun List<MovieItem>.mapToEntityList() = map {
    it.mapToEntity()
}