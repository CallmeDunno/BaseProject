package com.example.base.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.base.R

//@BindingAdapter("bindData:url")
//fun setImage(imageView: ImageView, link: String?) {
//    link?.let { Glide.with(imageView.context).load(link).into(imageView) }
//}
//
//@BindingAdapter("bindData:src")
//fun setImageViewResource(imageView: ImageView, resource: Int) {
//    imageView.setImageResource(resource)
//}
//
//@BindingAdapter("bindData:theme")
//fun setTheme(imageView: ImageView, i: Int) {
//    when (i) {
//        1->{imageView.setBackgroundResource(R.drawable.bg_theme_1)}
//        2->{imageView.setBackgroundResource(R.drawable.bg_theme_4)}
//        3->{imageView.setBackgroundResource(R.drawable.bg_theme_2)}
//        4->{imageView.setBackgroundResource(R.drawable.bg_theme_6)}
//        5->{imageView.setBackgroundResource(R.drawable.bg_theme_3)}
//        6->{imageView.setBackgroundResource(R.drawable.bg_theme_5)}
//    }
//}

//internal fun shimmerDrawable(): ShimmerDrawable {
//    val shimmer = Shimmer.AlphaHighlightBuilder().setDuration(800).setBaseAlpha(0.9f).setHighlightAlpha(1f).setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()
//
//    val shimmerDrawable = ShimmerDrawable().apply {
//        setShimmer(shimmer)
//    }
//
//    return shimmerDrawable
//}