package com.theapache64.leancorn.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.asLiveData
import com.theapache64.leancorn.R
import com.theapache64.leancorn.data.local.Category
import com.theapache64.leancorn.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BrowseSupportFragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.app_name)

        if (savedInstanceState == null) {
            prepareEntranceTransition()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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