package com.bitassyifaproject.acmc.home.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.databinding.ActivityHomeBinding
import com.bitassyifaproject.acmc.databinding.ActivityLoginBinding
import com.bitassyifaproject.acmc.order.ui.OrderActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private var backpressedTime = 0L
    lateinit var  navController: NavController
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = (mainFragment as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottomNavView,navController)


        binding.apply {
            btnOrder.setOnClickListener(this@HomeActivity)
            bottomNavView.background = null
            bottomNavView.menu.getItem(2).isEnabled =false
            bottomNavView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId){
                    R.id.home -> {
                        navController.navigate(R.id.action_global_to_homeFragment)
                        true
                    }
                    R.id.profilFragment -> {
                        navController.navigate(R.id.action_global_to_profilFragment)
                        true
                    }
                    R.id.status -> {
                        navController.navigate(R.id.action_global_to_orderListFragment)
                        true
                    }
                    else -> {
                        true
                    }
                }

            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnOrder -> {
                startActivity(Intent(this@HomeActivity,OrderActivity::class.java))
            }
        }
    }
}