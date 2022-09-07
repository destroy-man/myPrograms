package ru.korobeynikov.p1661createdrawable

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Rect
import android.graphics.Shader

class BitmapHexagonDrawable(bitmap: Bitmap) : HexagonDrawable() {

    private val mOriginBitmap = bitmap

    override fun onBoundsChange(bounds: Rect?) {
        if (bounds != null) {
            super.onBoundsChange(bounds)
            val bitmap = Bitmap.createScaledBitmap(mOriginBitmap, bounds.width(), bounds.height(), true)
            val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            getPaint().shader = shader
        }
    }
}