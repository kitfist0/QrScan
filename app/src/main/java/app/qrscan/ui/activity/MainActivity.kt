package app.qrscan.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import app.qrscan.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_generator, R.id.navigation_scanner, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, args: Bundle?) {
        when (destination.id) {
            R.id.navigation_details -> hideNavIfNecessary()
            else -> showNavIfNecessary()
        }
    }

    private fun showNavIfNecessary() = takeIf { bottomNavView.visibility == View.GONE }?.apply {
        bottomNavView.visibility = View.VISIBLE
    }

    private fun hideNavIfNecessary() = takeIf { bottomNavView.visibility == View.VISIBLE }?.apply {
        bottomNavView.visibility = View.GONE
    }
}
