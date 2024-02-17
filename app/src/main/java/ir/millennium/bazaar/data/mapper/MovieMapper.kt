package ir.millennium.bazaar.data.mapper

import ir.millennium.bazaar.data.model.remote.MovieItem
import ir.millennium.bazaar.domain.entity.MovieEntity

fun MovieEntity.mapToMovieItem() =
    MovieItem(title = this.title, posterPath = this.poster)

fun MovieItem.mapToMovieEntity() =
    MovieEntity(title = this.title, poster = this.posterPath)
