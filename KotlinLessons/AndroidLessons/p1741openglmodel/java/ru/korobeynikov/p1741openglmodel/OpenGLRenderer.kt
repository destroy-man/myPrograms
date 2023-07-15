package ru.korobeynikov.p1741openglmodel

import android.content.Context
import android.opengl.GLES32.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val POSITION_COUNT = 3
        private const val TIME = 10000L
    }

    private val mProjectMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mModelMatrix = FloatArray(16)
    private val mMatrix = FloatArray(16)
    private var programId = 0
    private var uColorLocation = 0
    private var uMatrixLocation = 0
    private lateinit var vertexData: FloatBuffer

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        glEnable(GL_DEPTH_TEST)
        val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
        createViewMatrix()
        prepareData()
        bindData()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        createProjectionMatrix(width.toFloat(), height.toFloat())
        bindMatrix()
    }

    private fun prepareData() {
        val vertices = floatArrayOf(-1f, -0.5f, 0.5f, 1f, -0.5f, 0.5f, 0f, 0.5f, 0.5f,
                                    -3f, 0f, 0f, 3f, 0f, 0f,
                                    0f, -3f, 0f, 0f, 3f, 0f,
                                    0f, 0f, -3f, 0f, 0f, 3f,
                                    0f, 0f, 0f, 1f, 1f, 1f)
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(vertices)
    }

    private fun bindData() {
        val aPositionLocation = glGetAttribLocation(programId, "a_Position")
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COUNT, GL_FLOAT, false,
            0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
        uColorLocation = glGetUniformLocation(programId, "u_Color")
        uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
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
        val eyeX = 2f
        val eyeY = 2f
        val eyeZ = 3f
        val centerX = 0f
        val centerY = 0f
        val centerZ = 0f
        val upX = 0f
        val upY = 1f
        val upZ = 0f
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

    private fun bindMatrix() {
        Matrix.multiplyMM(mMatrix, 0, mViewMatrix, 0, mModelMatrix, 0)
        Matrix.multiplyMM(mMatrix, 0, mProjectMatrix, 0, mMatrix, 0)
        glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        drawAxes()
        drawTriangle()
        glUniform4f(uColorLocation, 1f, 1f, 1f, 1f)
        glDrawArrays(GL_LINES, 9, 2)
    }

    private fun drawAxes() {
        Matrix.setIdentityM(mModelMatrix, 0)
        bindMatrix()
        glLineWidth(3f)
        glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        glDrawArrays(GL_LINES, 3, 2)
        glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        glDrawArrays(GL_LINES, 5, 2)
        glUniform4f(uColorLocation, 1f, 1f, 0f, 1f)
        glDrawArrays(GL_LINES, 7, 2)
    }

    private fun drawTriangle() {
        Matrix.setIdentityM(mModelMatrix, 0)
        setModelMatrix()
        bindMatrix()
        glUniform4f(uColorLocation, 0f, 1f, 0f, 1f)
        glDrawArrays(GL_TRIANGLES, 0, 3)
    }

    private fun setModelMatrix() {
        val angle = SystemClock.uptimeMillis().toFloat() % TIME / TIME * 360
        Matrix.rotateM(mModelMatrix, 0, angle, 0f, 1f, 1f)
    }
}