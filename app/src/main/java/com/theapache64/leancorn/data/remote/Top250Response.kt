package com.theapache64.leancorn.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "actors")
    val actors: List<String>,
    @Json(name = "desc")
    val desc: String, // The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.
    @Json(name = "directors")
    val directors: List<String>,
    @Json(name = "genre")
    val genre: List<String>,
    @Json(name = "image_url")
    val imageUrl: String, // https://m.media-amazon.com/images/M/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_.jpg
    @Json(name = "imdb_url")
    val imdbUrl: String, // /title/tt0050083/
    @Json(name = "name")
    val name: String, // 12 Angry Men
    @Json(name = "rating")
    val rating: Double, // 8.3
    @Json(name = "thumb_url")
    val thumbUrl: String, // https://m.media-amazon.com/images/M/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_UX182_CR0,0,182,268_AL__QL50.jpg
    @Json(name = "year")
    val year: Int? // 1957
)