package com.example.base.extensions

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import java.util.Locale

internal fun convertStringToFont(context: Context, idFont: Int): Typeface? {
    return ResourcesCompat.getFont(context, idFont)
}

internal fun upperFirstCharacter(str: String) : String {
    return str.capitalize(Locale.ROOT)
}