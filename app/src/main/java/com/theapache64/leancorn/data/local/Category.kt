package com.theapache64.leancorn.data.local

import com.theapache64.leancorn.data.remote.Movie

data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
)