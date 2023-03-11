package ru.korobeynikov.p0711preferencessimple

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import ru.korobeynikov.p0711preferencessimple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvInfo: TextView
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvInfo = binding.tvInfo
        sp = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onResume() {
        val notif = sp.getBoolean("notif", false)
        val address = sp.getString("address", "")
        val text = "Notificatios are ${
            if (notif) {
                "enabled, address = $address"
            } else "disabled"
        }"
        tvInfo.text = text
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mi = menu?.add(0, 1, 0, "Preferences")
        mi?.intent = Intent(this, PrefActivity::class.java)
        return super.onCreateOptionsMenu(menu)
    }
}