package com.theapache64.leancorn.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.lifecycle.asLiveData
import com.theapache64.leancorn.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BrowseSupportFragment() {

    private val viewModel: HomeViewModel by viewModels()

    companion object {
        fun newInstance() = HomeFragment()
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
                    println("Working: ${resource.data}")
                }
                is Resource.Error -> TODO()
            }
        }

    }
}