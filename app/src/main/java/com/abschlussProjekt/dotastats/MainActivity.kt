package com.abschlussProjekt.dotastats

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abschlussProjekt.dotastats.databinding.ActivityMainBinding

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

//            navController.currentBackStack.value.find { it.destination.id == menuItem.itemId }
//                ?.let {
//                    navController.popBackStack(menuItem.itemId, false)
//                } ?: navController.navigate(menuItem.itemId)
            true
        }
    }


    fun showLoadingScreen(show: Boolean) {
        when (show) {
            true -> {
                binding.loadingGifLayout.visibility = View.VISIBLE
                binding.bottomNavBar.menu[0].isEnabled = false
                binding.bottomNavBar.menu[1].isEnabled = false
            }
            else -> {
                binding.loadingGifLayout.visibility = View.INVISIBLE
                binding.bottomNavBar.menu[0].isEnabled = true
                binding.bottomNavBar.menu[1].isEnabled = true
            }
        }
    }
}
