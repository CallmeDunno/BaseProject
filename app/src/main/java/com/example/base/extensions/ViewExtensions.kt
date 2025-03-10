package com.example.base.extensions

import android.app.Activity
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.base.Constant

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    this.clearFocus()
}

internal fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager!!.toggleSoftInputFromWindow(
        view.applicationWindowToken,
        InputMethodManager.SHOW_FORCED, 0
    )
}

internal fun View.show() {
    visibility = View.VISIBLE
}

internal fun View.hide() {
    visibility = View.INVISIBLE
}

internal fun View.gone() {
    visibility = View.GONE
}

fun View.setAnimation(view: View, animationId: Int, isRun: Boolean) {
    if (isRun) {
        show()
        view.show()
        applyAnimation(view, animationId)
    } else {
        hide()
        view.hide()
        clearAnimation(view)
    }
}

private fun applyAnimation(view: View, animationId: Int) {
    val animation = AnimationUtils.loadAnimation(view.context, animationId)
    view.startAnimation(animation)
}

private fun clearAnimation(view: View) {
    view.animation?.cancel()
    view.clearAnimation()
}

internal fun View.setOnSingleClickListener(millis: Long = Constant.SINGLE_CLICK_TIME, event: () -> Unit) {
    this.setOnClickListener {
        if (System.currentTimeMillis() - Constant.lastTimeClick > millis) {
            Constant.lastTimeClick = System.currentTimeMillis()
            event.invoke()
        }
    }
}

internal fun TextView.gradientVertically(context: Context, startColor: Int, endColor: Int) {
    this.paint.shader = LinearGradient(0f, 0f, 0f, this.lineHeight.toFloat(), intArrayOf(startColor, endColor), null, Shader.TileMode.CLAMP)
    this.postInvalidate()
}

internal fun TextView.gradientHorizontally(startColor: Int, endColor: Int) {
    this.paint.shader = LinearGradient(0f, 0f, this.paint.measureText(this.text.toString()), this.textSize, intArrayOf(startColor, endColor), null, Shader.TileMode.CLAMP)
    this.postInvalidate()
}

internal fun Context.color(color: Int) = ContextCompat.getColor(this, color)

internal fun Context.drawable(drawable: Int) = ContextCompat.getDrawable(this, drawable)

internal fun Context.string(string: Int) = getString(string)

//internal fun Context.loadImage(path: Any, imageView: ImageView) = Glide.with(this).load(path).into(imageView)