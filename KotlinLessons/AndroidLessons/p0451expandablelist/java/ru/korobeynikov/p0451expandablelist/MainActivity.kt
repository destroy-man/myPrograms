package ru.korobeynikov.p0451expandablelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0451expandablelist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val ervMain = binding.ervMain
        val phonesHTC = mutableListOf(Phone("Sensation"), Phone("Desire"),
            Phone("Wildfire"), Phone("Hero"))
        val phonesSams = mutableListOf(Phone("Galaxy S II"), Phone("Galaxy Nexus"),
            Phone("Wave"))
        val phonesLG = mutableListOf(Phone("Optimus"), Phone("Optimus Link"),
            Phone("Optimus Black"), Phone("Optimus One"))
        val listGroups = ArrayList<GroupPhones>()
        listGroups.add(GroupPhones(name = "HTC", phones = phonesHTC))
        listGroups.add(GroupPhones(name = "Samsung", phones = phonesSams))
        listGroups.add(GroupPhones(name = "LG", phones = phonesLG))
        val layoutManager = LinearLayoutManager(this)
        ervMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(ervMain.context, layoutManager.orientation)
        ervMain.addItemDecoration(dividerItemDecoration)
        ervMain.adapter = PhoneAdapter(listGroups)
    }
}