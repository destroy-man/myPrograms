package ru.korobeynikov.p09espressoidlingresource

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p09espressoidlingresource.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var text: TextView
    private lateinit var presenter: Presenter
    private lateinit var simpleIdlingResource: SimpleIdlingResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        text = binding.text
        simpleIdlingResource = SimpleIdlingResource()
        presenter = Presenter(DataRepository(), simpleIdlingResource)
        presenter.attachView(this)
    }

    fun clickButton() {
        presenter.onButtonClick()
    }

    fun setData(s: String) {
        text.text = s
    }

    fun getIdlingResource() = simpleIdlingResource

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}