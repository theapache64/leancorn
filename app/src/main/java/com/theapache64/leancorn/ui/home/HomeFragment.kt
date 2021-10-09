package com.theapache64.leancorn.ui.home

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import com.theapache64.leancorn.R
import com.theapache64.leancorn.data.local.Category
import com.theapache64.leancorn.data.remote.Movie
import com.theapache64.leancorn.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BrowseSupportFragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.app_name)

        if (savedInstanceState == null) {
            prepareEntranceTransition()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnItemViewClickedListener { _, item, _, _ ->
            item as Movie
            viewModel.onMovieClicked(item)
        }

        setDynamicBackground()
    }

    private fun observeData() {
        viewModel.moviesResponse.asLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Idle -> {
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    displayData(resource.data)
                    startEntranceTransition()
                }
                is Resource.Error -> TODO()
            }
        }

        viewModel.navigateToDetail.asLiveData().observe(viewLifecycleOwner) {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToDetail(
                    it.category,
                    it.movie
                )
            )
        }
    }

    private fun setDynamicBackground() {
        setOnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            if (itemViewHolder?.view != null) {
                val bitmapDrawable =
                    (itemViewHolder.view as ImageCardView).mainImageView.drawable as? BitmapDrawable
                if (bitmapDrawable != null) {
                    Palette.from(bitmapDrawable.bitmap).generate { palette ->
                        palette?.darkVibrantSwatch?.let { swatch ->
                            backgroundManager.color = swatch.rgb
                        }
                    }
                }
            }
        }
    }

    private fun displayData(categories: List<Category>) {
        val adapter = ArrayObjectAdapter(ListRowPresenter())
        for (category in categories) {
            val headerItem = HeaderItem(category.id, category.genre)
            val rowAdapter = ArrayObjectAdapter(PosterPresenter())
            for (movie in category.movies) {
                rowAdapter.add(movie)
            }
            adapter.add(ListRow(headerItem, rowAdapter))
        }
        this.adapter = adapter
    }
}