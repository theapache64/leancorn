package com.theapache64.leancorn.data.local

import android.os.Parcelable
import com.theapache64.leancorn.data.remote.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
) : Parcelable