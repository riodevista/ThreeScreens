package ru.looktv.launcher.data

import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.looktv.launcher.core.network.createHttpClient
import ru.looktv.launcher.data.models.Movie

private const val BASE_URL = "https://api.sandbox.start.film/"
private const val DEV_TOKEN = "F50YJNNRFLcERsTQLrgX0SunrPEIyWTK"

class MoviesRepository {

    private val httpClient = createHttpClient(BASE_URL)

    suspend fun getMoviesList(): List<Movie> {
        val result = httpClient.get("partner/meta/looktv") {
            parameter("apikey", DEV_TOKEN)
        }.body<List<MovieDto>>().take(5).map {
            Movie(
                it.id,
                it.alias,
                "https://api.sandbox.start.film${it.background.image1x}"
            )
        }
        return result
    }
}
