package ru.korobeynikov.p0311simpleintents

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0311simpleintents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnWeb -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"))
                startActivity(intent)
            }
            R.id.btnMap -> {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse("geo:55.754283,37.62002")
                startActivity(intent)
            }
            R.id.btnCall -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:12345")
                startActivity(intent)
            }
        }
    }
}