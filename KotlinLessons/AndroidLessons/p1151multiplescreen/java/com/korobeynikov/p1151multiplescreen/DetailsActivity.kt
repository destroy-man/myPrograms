package com.korobeynikov.p1151multiplescreen

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class DetailsActivity:FragmentActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
            finish()
            return
        }
        if(savedInstanceState==null){
            val details=DetailsFragment.newInstance(intent.getIntExtra("position",0))
            supportFragmentManager.beginTransaction().add(android.R.id.content,details).commit()
        }
    }
}