package com.abschlussProjekt.dotastats

import android.os.Bundle
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

        binding.bottomNavBar.setOnItemSelectedListener {
            navController.navigate(it.itemId)
            false
        }
    }
}
