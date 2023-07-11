package ru.korobeynikov.p1721openglperspectivefrustumortho

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
    }

    private val mProjectMatrix = FloatArray(16)
    private var programId = 0
    private var uColorLocation = 0
    private var aPositionLocation = 0
    private var uMatrixLocation = 0
    private val vertexData: FloatBuffer

    init {
        val z1 = -1f
        val z2 = -1f
        val vertices = floatArrayOf(-0.7f, -0.5f, z1, 0.3f, -0.5f, z1, -0.2f, 0.3f, z1,
                                    -0.3f, -0.4f, z2, 0.7f, -0.4f, z2, 0.2f, 0.4f, z2)
        vertexData = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()
        vertexData.put(vertices)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glEnable(GL_DEPTH_TEST)
        glClearColor(0f, 0f, 0f, 1f)
        val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
        val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
        programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
        glUseProgram(programId)
        bindData()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        bindMatrix(width.toFloat(), height.toFloat())
    }

    private fun bindData() {
        aPositionLocation = glGetAttribLocation(programId, "a_Position")
        vertexData.position(0)
        glVertexAttribPointer(aPositionLocation, POSITION_COUNT, GL_FLOAT, false, 0, vertexData)
        glEnableVertexAttribArray(aPositionLocation)
        uColorLocation = glGetUniformLocation(programId, "u_Color")
        uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glUniform4f(uColorLocation, 0f, 1f, 0f, 1f)
        glDrawArrays(GL_TRIANGLES, 0, 3)
        glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        glDrawArrays(GL_TRIANGLES, 3, 3)
    }

    private fun bindMatrix(width: Float, height: Float) {
        val ratio: Float
        var left = -1f
        var right = 1f
        var bottom = -1f
        var top = 1f
        if (width > height) {
            ratio = width / height
            left *= ratio
            right *= ratio
        } else {
            ratio = height / width
            bottom *= ratio
            top *= ratio
        }
        Matrix.frustumM(mProjectMatrix, 0, left, right, bottom, top, 1f, 8f)
        glUniformMatrix4fv(uMatrixLocation, 1, false, mProjectMatrix, 0)
    }
}