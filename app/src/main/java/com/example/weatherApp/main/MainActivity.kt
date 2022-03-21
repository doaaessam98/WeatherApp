package com.example.weatherApp.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.weatherApp.R
import com.example.weatherApp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var navHostFragment:NavHostFragment= supportFragmentManager
           .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        var navController:NavController=navHostFragment.navController

        val appBarConfiguration:AppBarConfiguration= AppBarConfiguration
            .Builder(navController.graph).setOpenableLayout(binding.drawerLayout).build()

         setupWithNavController(binding.toolBar,navController,appBarConfiguration)

        setupWithNavController(binding.navView, navController)




   // binding.toolBar.overflowIcon = getDrawable(R.drawable.ic_menu)
    binding.toolBar.navigationIcon?.setTint(Color.parseColor("#130e51"))


    binding.navView.setNavigationItemSelectedListener(this)
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_cloud_24)
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
//            R.id.splashFragment -> {
//                binding.toolBar.visibility = View.GONE
//              //  binding.drawerLayout.visibility=View.GONE
//            }
//            R.id.homeFragment -> {
//                binding.toolBar.visibility=View.VISIBLE
////                binding.toolBar.setNavigationIcon(R.drawable.ic_menu)
//            }
//            R.id.userDilogFragment -> {
//                binding.toolBar.visibility=View.GONE
//               // binding.drawerLayout.visibility=View.GONE
//            }
//            else -> {
//                binding.toolbar.setNavigationIcon(R.drawable.ic_back)
//                binding.toolbar.show()
//            }
//        }
        }
    }
}


fun openDrawer(view:View){
      if(binding.drawerLayout.isDrawerOpen(Gravity.LEFT)){
          binding.drawerLayout.closeDrawer(Gravity.LEFT)
       }

      else{
          binding.drawerLayout.openDrawer(Gravity.LEFT)
        }

    }


        override fun onBackPressed() {

                super.onBackPressed()

        }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment ->{
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                binding.toolBar.setNavigationIcon(R.drawable.ic_cloud)
            }


            R.id.favoriteFragment -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.favoriteFragment)
                binding.toolBar.setNavigationIcon(R.drawable.ic_back)

            }
            R.id.settingFragment -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.settingFragment)
                binding.toolBar.setNavigationIcon(R.drawable.ic_back)

            }
            R.id.alarmFragment -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.alarmFragment)
                binding.toolBar.setNavigationIcon(R.drawable.ic_back)

            }
            R.id.setAlarmsFragment -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.alarmFragment)
                binding.toolBar.setNavigationIcon(R.drawable.ic_back)

            }
        }
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) || super.onOptionsItemSelected(
            item
        )
    }
    }


