package com.theapache64.leancorn.ui.feature.home

import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import coil.load
import coil.size.Scale
import com.theapache64.leancorn.R
import com.theapache64.leancorn.data.remote.Movie

class PosterPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val imageCardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            cardType = BaseCardView.CARD_TYPE_MAIN_ONLY
            with(mainImageView) {
                val posterWidth = parent.resources.getDimension(R.dimen.poster_width).toInt()
                val posterHeight = parent.resources.getDimension(R.dimen.poster_height).toInt()
                layoutParams = BaseCardView.LayoutParams(posterWidth, posterHeight)
            }
        }
        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val movie = item as Movie
        with(viewHolder.view as ImageCardView) {
            val posterWidth = resources.getDimension(R.dimen.poster_width).toInt()
            val posterHeight = resources.getDimension(R.dimen.poster_height).toInt()

            mainImageView.load(
                uri = movie.imageUrl,
                builder = {
                    scale(Scale.FIT)
                    size(posterWidth, posterHeight)
                    allowHardware(false)
                })
            titleText = movie.name
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            mainImage = null
        }
    }
}