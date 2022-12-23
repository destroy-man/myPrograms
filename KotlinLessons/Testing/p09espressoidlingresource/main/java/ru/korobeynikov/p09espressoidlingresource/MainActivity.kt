package ru.korobeynikov.p09espressoidlingresource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.test.espresso.IdlingResource
import ru.korobeynikov.p09espressoidlingresource.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private lateinit var simpleIdlingResource: SimpleIdlingResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
        presenter = MainPresenter(DataRepository())
        presenter.attachView(this)
        simpleIdlingResource = SimpleIdlingResource()
        presenter.setIdlingResource(simpleIdlingResource)
    }

    fun setData(s: String) {
        binding.text.text = s
    }

    fun buttonClick() {
        presenter.onButtonClick()
    }

    fun getIdlingResource(): IdlingResource {
        return simpleIdlingResource
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}