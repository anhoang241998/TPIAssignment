package com.annguyenhoang.tpiassignment.views

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.databinding.ActivityTouristBinding

class TouristActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTouristBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(getColor(R.color.blue_400)))
        binding = ActivityTouristBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statusBarMargin()
        configureAppBar()
    }

    private fun statusBarMargin() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appToolbar) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun configureAppBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.tourist_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.appToolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.appToolbar)

        binding.appToolbar.setNavigationOnClickListener {
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }
    }

}