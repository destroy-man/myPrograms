package ru.korobeynikov.p12rxbinding

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxbinding2.view.RxMenuItem
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import ru.korobeynikov.p12rxbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        RxRadioGroup.checkedChanges(binding.radioGroup).skipInitialValue().subscribe { int ->
            log("checked $int")
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity, menu)
        if (menu != null)
            RxMenuItem.clicks(menu.findItem(R.id.action_item1)).subscribe {
                log("menu item 1 clicked")
            }
        return true
    }

    private fun log(text: String) = Log.d("myLogs", text)
}