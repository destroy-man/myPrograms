package ru.korobeynikov.p1151multiplescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1151multiplescreen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TitlesFragment.OnItemClickListener {

    private var position = 0
    private var withDetails = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        if (savedInstanceState != null)
            position = savedInstanceState.getInt("position")
        withDetails = binding.cont != null
        if (withDetails)
            showDetails(position)
    }

    private fun showDetails(pos: Int) {
        if (withDetails) {
            var details = supportFragmentManager.findFragmentById(R.id.cont) as DetailsFragment?
            if (details == null || details.getPosition() != pos) {
                details = DetailsFragment.newInstance(pos)
                supportFragmentManager.beginTransaction().replace(R.id.cont, details).commit()
            }
        } else
            startActivity(Intent(this, DetailsActivity::class.java)
                .putExtra("position", position))
    }

    override fun itemClick(position: Int) {
        this.position = position
        showDetails(position)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position", position)
    }
}