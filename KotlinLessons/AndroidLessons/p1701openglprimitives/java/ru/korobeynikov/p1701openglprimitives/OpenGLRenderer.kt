package ru.korobeynikov.p1701openglprimitives

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private var programId = 0
    private lateinit var vertexData: FloatBuffer
    private var uColorLocation = 0
    private var aPositionLocation = 0

    init {
        prepareData()
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
        bindData()
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    private fun prepareData() {
        val vertices = floatArrayOf(
            -0.9f, 0.8f, -0.9f, 0.2f, -0.5f, 0.8f,
            -0.6f, 0.2f, -0.2f, 0.2f, -0.2f, 0.8f,
            0.1f, 0.8f, 0.1f, 0.2f, 0.5f, 0.8f,
            0.1f, 0.2f, 0.5f, 0.2f, 0.5f, 0.8f,
            -0.7f, -0.1f, 0.7f, -0.1f,
            -0.6f, -0.2f, 0.6f, -0.2f,
            -0.5f, -0.3f,
            0f, -0.3f,
            0.5f, -0.3f
        )
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(vertices)
    }

    private fun bindData() {
        uColorLocation = glGetUniformLocation(programId, "u_Color")
        glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        aPositionLocation = glGetAttribLocation(programId, "a_Position")
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onDrawFrame(p0: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
        glLineWidth(5f)
        glDrawArrays(GL_TRIANGLES, 0, 12)
        glDrawArrays(GL_LINES, 12, 4)
        glDrawArrays(GL_POINTS, 16, 3)
    }
}