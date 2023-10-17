package com.abschlussProjekt.dotastats

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abschlussProjekt.dotastats.databinding.ActivityMainBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import com.abschlussProjekt.dotastats.ui.recentmatches.RecentMatchesFragmentDirections

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: DotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        binding.bottomNavBar.setupWithNavController(navController)

       binding.bottomNavBar.setOnItemSelectedListener {
            navController.navigate(it.itemId)
        true
        }



        // Wechsle zur Detailansicht, wenn Match angelickt wird
        viewModel.detailProMatch.observe(this)
        {
            navController
                .navigate(RecentMatchesFragmentDirections.actionRecentMatchesFragmentToMatchDetailFragment())
        }
    }
}
