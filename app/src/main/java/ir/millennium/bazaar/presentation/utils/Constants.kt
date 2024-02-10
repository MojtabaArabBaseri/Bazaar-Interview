package ir.millennium.bazaar.presentation.utils


object Constants {

    const val SPLASH_DISPLAY_LENGTH = 3500.toLong()

    var BACK_PRESSED: Long = 0

    const val MOVIE_DATABASE_NAME = "SampleProjectDB.db"

    const val USER_PREFERENCES_REPOSITORY = "user_preferences_repository"

    const val BASIC_URL = "https://api.themoviedb.org/3/"

    const val BASIC_URL_IMAGE = "https://image.tmdb.org/t/p/w500"

    const val API_KEY = "1f02e97880074860bd0aa75e83801ff4"
    const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNTYyZDY4MWI5YTRmODMwMTg3ZmY1YjMwZjc3MzgxZCIsInN1YiI6IjY1YzNjZDFlOTVhY2YwMDE2MjFjNmU3ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WrY5PLEWNNmPPIj_KAdgh30VxMzCMYGpuThi76dhnY0"

    const val HEADER_CACHE_CONTROL = "Cache-Control"

    const val HEADER_PRAGMA = "Pragma"

    const val CACHE_SIZE_FOR_RETROFIT = (5 * 1024 * 1024).toLong()
}