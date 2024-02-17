package ir.millennium.bazaar.domain.usecase

import androidx.paging.Pager
import ir.millennium.bazaar.domain.entity.MovieEntity
import javax.inject.Inject

open class GetMovieListUseCase @Inject constructor(private val pager: Pager<Int, MovieEntity>) {
    operator fun invoke() = pager.flow
}