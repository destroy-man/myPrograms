package ru.korobeynikov.p1751opengltextures

import android.content.Context
import android.opengl.GLES32.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val POSITION_COUNT = 3
        private const val TEXTURE_COUNT = 2
        private const val STRIDE = (POSITION_COUNT + TEXTURE_COUNT) * 4
    }

    private val mProjectMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mMatrix = FloatArray(16)
    private var aPositionLocation = 0
    private var aTextureLocation = 0
    private var uTextureUnitLocation = 0
    private var uMatrixLocation = 0
    private var programId = 0
    private var texture = 0
    private var texture2 = 0
    private lateinit var vertexData: FloatBuffer

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        glEnable(GL_DEPTH_TEST)
        createAndUseProgram()
        getLocations()
        prepareData()
        bindData()
        createViewMatrix()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        createProjectionMatrix(width.toFloat(), height.toFloat())
        bindMatrix()
    }

    private fun prepareData() {
        val vertices = floatArrayOf(-1f, 1f, 1f, 0f, 0f,
                                    -1f, -1f, 1f, 0f, 1f,
                                    1f, 1f, 1f, 1f, 0f,
                                    1f, -1f, 1f, 1f, 1f,
                                    -1f, 4f, 1f, 0f, 0f,
                                    -1f, 2f, 1f, 0f, 1f,
                                    1f, 4f, 1f, 0.5f, 0f,
                                    1f, 2f, 1f, 0.5f, 1f)
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(vertices)
        texture = TextureUtils.loadTexture(context, R.drawable.box)
        texture2 = TextureUtils.loadTexture(context, R.drawable.boxes)
    }

    private fun createAndUseProgram() {
        val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
    }

    private fun getLocations() {
        aPositionLocation = glGetAttribLocation(programId, "a_Position")
        aTextureLocation = glGetAttribLocation(programId, "a_Texture")
        uTextureUnitLocation = glGetUniformLocation(programId, "u_TextureUnit")
        uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
    }

    private fun bindData() {
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COUNT, GL_FLOAT, false,
            STRIDE, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
        vertexData.position(POSITION_COUNT)
        glVertexAttribPointer(aTextureLocation, TEXTURE_COUNT, GL_FLOAT, false, STRIDE, vertexData)
        glEnableVertexAttribArray(aTextureLocation)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture)
        glUniform1i(uTextureUnitLocation, 0)
    }

    private fun createProjectionMatrix(width: Float, height: Float) {
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        if (width > height) {
            val ratio = width / height
            left *= ratio
            right *= ratio
        } else {
            val ratio = height / width
            bottom *= ratio
            top *= ratio
        }
        Matrix.frustumM(mProjectMatrix, 0, left, right, bottom, top, 2f, 12f)
    }

    private fun createViewMatrix() {
        val eyeX = 0f
        val eyeY = 0f
        val eyeZ = 7f
        val centerX = 0f
        val centerY = 0f
        val centerZ = 0f
        val upX = 0f
        val upY = 1f
        val upZ = 0f
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

    private fun bindMatrix() {
        Matrix.multiplyMM(mMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0)
        glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glBindTexture(GL_TEXTURE_2D, texture)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        glBindTexture(GL_TEXTURE_2D, texture2)
        glDrawArrays(GL_TRIANGLE_STRIP, 4, 4)
    }
}