package com.abschlussProjekt.dotastats

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abschlussProjekt.dotastats.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        binding.bottomNavBar.setupWithNavController(navController)

        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->

            if (navController.currentDestination!!.id != menuItem.itemId) {
                navController.navigate(menuItem.itemId)
            }
            true
        }
    }


    fun showLoadingScreen(show: Boolean, delay: Long = 0L) {

        lifecycleScope.launch {
            delay(delay)

            when (show) {
                true -> {
                    binding.loadingGifLayout.visibility = View.VISIBLE
                    disableBottomNavBar(true)
                }
                else -> {
                    binding.loadingGifLayout.visibility = View.INVISIBLE
                    disableBottomNavBar(false)
                }
            }
        }
    }

    private fun disableBottomNavBar(disable: Boolean) {
        binding.bottomNavBar.menu[0].isEnabled = !disable
        binding.bottomNavBar.menu[1].isEnabled = !disable
    }

}
