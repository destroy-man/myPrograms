package ru.korobeynikov.p1831constraintset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionManager
import ru.korobeynikov.p1831constraintset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var someMargin = 0
    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        someMargin = resources.getDimension(R.dimen.some_margin).toInt()
        constraintLayout = binding.container
    }

    fun buttonClick() {
        val set = ConstraintSet()
        set.clone(constraintLayout)
        changeConstraints(set)
        TransitionManager.beginDelayedTransition(constraintLayout)
        set.applyTo(constraintLayout)
    }

    private fun changeConstraints(set: ConstraintSet) {
        with(set) {
            create(R.id.guideline, ConstraintSet.VERTICAL_GUIDELINE)
            setGuidelinePercent(R.id.guideline, 0.2f)
            connect(R.id.textView, ConstraintSet.START, R.id.guideline, ConstraintSet.END, 0)
            connect(R.id.textView2, ConstraintSet.START, R.id.guideline, ConstraintSet.END, 0)
            connect(R.id.textView3, ConstraintSet.START, R.id.guideline, ConstraintSet.END, 0)
            setMargin(R.id.textView, ConstraintSet.START, 0)
            setMargin(R.id.textView2, ConstraintSet.START, 0)
            setMargin(R.id.textView3, ConstraintSet.START, 0)
        }
    }
}