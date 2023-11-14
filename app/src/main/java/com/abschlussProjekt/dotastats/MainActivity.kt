package com.abschlussProjekt.dotastats

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abschlussProjekt.dotastats.databinding.ActivityMainBinding
import com.abschlussProjekt.dotastats.ui.DotaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->

            if (navController.currentDestination!!.id != menuItem.itemId) {
                navController.navigate(menuItem.itemId)
            }
            true
        }


        viewModel.errorMessage.observe(this) {
            it?.let { errorString ->
                showErrorScreen(true, errorString)
            }
            if (it == null){
                showErrorScreen(false, "")
            }

        }

        binding.backButton.setOnClickListener {
            onBackPressed()
            showLoadingScreen(false)
            disableBottomNavBar(false)
            viewModel.resetError()
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

    private fun showErrorScreen(show: Boolean, message: String) {

        when (show) {
            true -> {
                binding.errorMessage.text = message
                binding.errorScreen.visibility = View.VISIBLE
                binding.loadingGifLayout.visibility = View.INVISIBLE
                disableBottomNavBar(true)
            }

            false -> {
                binding.errorMessage.text = ""
                binding.errorScreen.visibility = View.INVISIBLE
                disableBottomNavBar(false)
            }
        }

    }

}
