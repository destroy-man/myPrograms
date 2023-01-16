package ru.korobeynikov.p0191simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0191simplecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val menuResetId = 1
    private val menuQuitId = 2
    private val oper = StringBuilder()
    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMult: Button
    private lateinit var btnDiv: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        etNum1 = binding.etNum1
        etNum2 = binding.etNum2
        btnAdd = binding.btnAdd
        btnSub = binding.btnSub
        btnMult = binding.btnMult
        btnDiv = binding.btnDiv
        tvResult = binding.tvResult
        binding.view = this
    }

    fun clickButton(v: View) {
        var result = 0f
        if (etNum1.text.toString().isEmpty() || etNum2.text.toString().isEmpty()) {
            Toast.makeText(this, "Необходимо заполнить оба поля!", Toast.LENGTH_SHORT).show()
            return
        }
        val num1 = etNum1.text.toString().toFloat()
        val num2 = etNum2.text.toString().toFloat()
        when (v.id) {
            R.id.btnAdd -> {
                oper.clear()
                oper.append("+")
                result = num1 + num2
            }
            R.id.btnSub -> {
                oper.clear()
                oper.append("-")
                result = num1 - num2
            }
            R.id.btnMult -> {
                oper.clear()
                oper.append("*")
                result = num1 * num2
            }
            R.id.btnDiv -> {
                if (num2 != 0f) {
                    oper.clear()
                    oper.append("/")
                    result = num1 / num2
                } else {
                    Toast.makeText(this, "На ноль делить нельзя!", Toast.LENGTH_SHORT).show()
                    return
                }
            }
        }
        tvResult.text = "$num1 $oper $num2 = $result"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, menuResetId, 0, "Reset")
        menu?.add(0, menuQuitId, 0, "Quit")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            menuResetId -> {
                etNum1.setText("")
                etNum2.setText("")
                tvResult.text = ""
            }
            menuQuitId -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}