package ru.korobeynikov.p1711openglcolor

import android.content.Context
import android.opengl.GLES32.*
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private var programId = 0
    private var aColorLocation = 0
    private var aPositionLocation = 0
    private val vertexData: FloatBuffer

    init {
        val vertices = floatArrayOf(-0.5f, -0.2f, 1f, 0f, 0f,
                                    0f, 0.2f, 0f, 1f, 0f,
                                    0.5f, -0.2f, 0f, 0f, 1f)
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1f)
        val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
        bindData()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    private fun bindData() {
        aPositionLocation = glGetAttribLocation(programId, "a_Position")
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 20, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
        aColorLocation = glGetAttribLocation(programId, "a_Color")
        vertexData.position(2)
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 20, vertexData)
        glEnableVertexAttribArray(aColorLocation)
    }

    override fun onDrawFrame(gl: GL10?) {
        glLineWidth(5f)
        glDrawArrays(GL_TRIANGLES, 0, 3)
    }
}