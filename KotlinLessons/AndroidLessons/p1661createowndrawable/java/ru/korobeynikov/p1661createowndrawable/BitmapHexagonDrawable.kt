package ru.korobeynikov.p1661createowndrawable

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Rect
import android.graphics.Shader

class BitmapHexagonDrawable(private val mOriginBitmap: Bitmap) : HexagonDrawable() {
    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.let {
            val bitmap = Bitmap.createScaledBitmap(mOriginBitmap, it.width(), it.height(), true)
            getPaint().shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
    }
}