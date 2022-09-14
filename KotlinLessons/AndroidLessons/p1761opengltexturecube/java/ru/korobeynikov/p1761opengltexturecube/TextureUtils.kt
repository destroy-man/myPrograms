package ru.korobeynikov.p1761opengltexturecube

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils

class TextureUtils {
    companion object {
        fun loadTextureCube(context: Context, resourceId: IntArray): Int {
            val textureIds = IntArray(1)
            glGenTextures(1, textureIds, 0)
            if (textureIds[0] == 0) return 0
            val options = BitmapFactory.Options()
            options.inScaled = false
            val bitmaps = Array<Bitmap?>(6) { null }
            for (i in 0 until 6) {
                bitmaps[i] = BitmapFactory.decodeResource(context.resources, resourceId[i], options)
                if (bitmaps[i] == null) {
                    glDeleteTextures(1, textureIds, 0)
                    return 0
                }
            }
            glActiveTexture(GL_TEXTURE0)
            glBindTexture(GL_TEXTURE_CUBE_MAP, textureIds[0])
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, bitmaps[0], 0)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, bitmaps[1], 0)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, bitmaps[2], 0)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, bitmaps[3], 0)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, bitmaps[4], 0)
            GLUtils.texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, bitmaps[5], 0)
            for (bitmap in bitmaps)
                bitmap?.recycle()
            glBindTexture(GL_TEXTURE_CUBE_MAP, 0)
            return textureIds[0]
        }
    }
}