package com.theapache64.leancorn.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.leancorn.data.local.Category
import com.theapache64.leancorn.data.remote.Movie
import com.theapache64.leancorn.data.repo.MoviesRepo
import com.theapache64.leancorn.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {
    private val _moviesResponse =
        MutableStateFlow<Resource<List<Category>>>(Resource.Idle())
    val moviesResponse = _moviesResponse.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            with(_moviesResponse) {
                tryEmit(Resource.Loading())
                tryEmit(Resource.Success(moviesRepo.getMovies().categorize()))
                // TODO : Handle network error
            }
        }
    }

    /**
     * To convert movie list to categorized feed
     */
    fun List<Movie>.categorize(): List<Category> {
        val genreSet = mutableSetOf<String>()
        for (movie in this) {
            for (genre in movie.genre) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<Category>()
        for ((index, genre) in genreSet.withIndex()) {
            val genreMovies = this.filter { it.genre.contains(genre) }
            feedItems.add(
                Category(
                    index.toLong(),
                    genre,
                    genreMovies
                )
            )
        }
        return feedItems
    }
}
