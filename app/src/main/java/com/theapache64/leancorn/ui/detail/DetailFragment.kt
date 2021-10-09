package com.theapache64.leancorn.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DetailsSupportFragment() {
    val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.args.asLiveData().observe(viewLifecycleOwner) {
            // Bind to detail
            // Create More like this
        }
    }
}