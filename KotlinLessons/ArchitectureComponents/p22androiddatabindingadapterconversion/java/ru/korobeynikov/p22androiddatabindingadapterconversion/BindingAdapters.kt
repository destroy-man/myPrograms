package ru.korobeynikov.p22androiddatabindingadapterconversion

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("app:url", "app:errorImage")
        fun loadImage(view: ImageView, url: String, errorImage: Drawable) =
            Picasso.get().load(url).error(errorImage).into(view)
    }
}