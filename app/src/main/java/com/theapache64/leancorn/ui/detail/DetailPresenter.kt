package com.theapache64.leancorn.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import com.theapache64.leancorn.data.remote.Movie
import com.theapache64.leancorn.databinding.DetailMetaBinding

class DetailPresenter : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DetailMetaBinding.inflate(inflater, parent, false)
        binding.root.tag = binding
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Movie
        val binding = viewHolder.view.tag as DetailMetaBinding
        with(binding) {
            tvDesc.text = movie.desc
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // TODO:
    }
}