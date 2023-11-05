package com.abschlussProjekt.dotastats

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

            Log.e(
                "Stack",
                navController.currentBackStack.value.joinToString {"${it.destination.displayName}\n" })

            if (navController.currentDestination!!.id != menuItem.itemId){
                navController.navigate(menuItem.itemId)
            }

//            navController.currentBackStack.value.find { it.destination.id == menuItem.itemId }
//                ?.let {
//                    navController.popBackStack(menuItem.itemId, false)
//                } ?: navController.navigate(menuItem.itemId)
            true
        }
    }
}
