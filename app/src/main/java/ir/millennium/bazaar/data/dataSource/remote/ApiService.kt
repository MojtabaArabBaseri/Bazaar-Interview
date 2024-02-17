package ir.millennium.bazaar.data.dataSource.remote

import ir.millennium.bazaar.data.model.remote.MovieListModel
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface ApiService {

    @GET("movie/upcoming")
    suspend fun getMovieList(
        @HeaderMap headerMap: MutableMap<String, String>,
        @Query("page") page: Int
    ): MovieListModel

}