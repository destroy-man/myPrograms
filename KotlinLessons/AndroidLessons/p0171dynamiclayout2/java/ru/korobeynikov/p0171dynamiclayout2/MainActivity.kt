package ru.korobeynikov.p0171dynamiclayout2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0171dynamiclayout2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var clMain: ConstraintLayout
    private lateinit var rgGravity: RadioGroup
    private lateinit var etName: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnClear: Button
    private val wrapContent = ConstraintLayout.LayoutParams.WRAP_CONTENT
    private val listCreatedButtons = arrayListOf<Button>()
    private var lastId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        clMain = binding.clMain
        rgGravity = binding.rgGravity
        etName = binding.etName
        btnCreate = binding.btnCreate
        btnClear = binding.btnClear
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnCreate -> {
                val clParams = ConstraintLayout.LayoutParams(wrapContent, wrapContent)
                when (rgGravity.checkedRadioButtonId) {
                    R.id.rbLeft -> clParams.startToStart = ConstraintSet.PARENT_ID
                    R.id.rbCenter -> {
                        clParams.startToStart = ConstraintSet.PARENT_ID
                        clParams.endToEnd = ConstraintSet.PARENT_ID
                    }
                    R.id.rbRight -> clParams.endToEnd = ConstraintSet.PARENT_ID
                }
                if (lastId != -1)
                    clParams.topToBottom = lastId
                else
                    clParams.topToBottom = R.id.etName
                lastId = View.generateViewId()
                val btnNew = Button(this)
                btnNew.id = lastId
                btnNew.text = etName.text.toString()
                listCreatedButtons.add(btnNew)
                clMain.addView(btnNew, clParams)
            }
            R.id.btnClear -> {
                for (button in listCreatedButtons)
                    clMain.removeView(button)
                Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show()
                lastId = -1
            }
        }
    }
}