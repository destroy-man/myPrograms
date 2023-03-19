package ru.korobeynikov.p0771tabintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import ru.korobeynikov.p0771tabintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val tabLayout = binding.tablayout
        val pager = binding.pager
        pager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = "Вкладка ${position + 1}"
        }.attach()
    }
}