package ru.korobeynikov.p26navigationparameters

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import ru.korobeynikov.p26navigationparameters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d(TAG, "onNavigate to ${destination.label}")
        }
    }

    fun fragment1NextClick() {
        val builder = NavOptions.Builder()
        val navOptions = builder.setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right).build()
        navController.navigate(R.id.fragment2, null, navOptions)
    }

    fun fragment1BackClick() {}

    fun fragment2NextClick() = navController.navigate(R.id.action_fragment2_to_fragment3)

    fun fragment2BackStack() = navController.popBackStack()

    fun fragment3NextClick() {
        val bundle = Bundle()
        bundle.putString("folder", "camera")
        bundle.putString("id", "100")
        navController.navigate(R.id.secondActivity, bundle)
    }

    fun fragment3BackClick() = navController.popBackStack()
}