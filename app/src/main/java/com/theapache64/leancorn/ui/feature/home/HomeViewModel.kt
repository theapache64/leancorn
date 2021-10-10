package com.theapache64.leancorn.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.leancorn.data.local.Category
import com.theapache64.leancorn.data.remote.Movie
import com.theapache64.leancorn.data.repo.MoviesRepo
import com.theapache64.leancorn.ui.feature.detail.DetailFragmentArgs
import com.theapache64.leancorn.util.Resource
import com.theapache64.leancorn.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _navigateToDetail = mutableEventFlow<DetailFragmentArgs>()
    val navigateToDetail = _navigateToDetail.asSharedFlow()

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
    private fun List<Movie>.categorize(): List<Category> {
        val genreSet = mutableSetOf<String>()
        for (movie in this) {
            for (genre in movie.genre) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<Category>()
        for ((index, genre) in genreSet.withIndex()) {
            val categoryId = index.toLong()
            // TODO: Optimize
            val genreMovies = this.filter { it.genre.contains(genre) }
                .map { movie -> movie.copy().apply { this.categoryId = categoryId } }
                .sortedByDescending { it.year ?: 0 }

            feedItems.add(
                Category(
                    categoryId,
                    genre,
                    genreMovies
                )
            )
        }
        return feedItems
    }

    fun onMovieClicked(movie: Movie) {
        if (moviesResponse.value is Resource.Success) {
            val clickedCategory = (moviesResponse.value as Resource.Success<List<Category>>).data
                .find { it.id == movie.categoryId }!!

            // Navigating to detail
            _navigateToDetail.tryEmit(
                DetailFragmentArgs(clickedCategory, movie)
            )
        }
    }
}
