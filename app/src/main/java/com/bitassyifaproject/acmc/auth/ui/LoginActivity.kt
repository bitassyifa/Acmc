package com.bitassyifaproject.acmc.auth.ui

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.bitassyifaproject.acmc.R
import com.bitassyifaproject.acmc.auth.data.LoginRepo
import com.bitassyifaproject.acmc.config.AcmcApp
import com.bitassyifaproject.acmc.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var dataLogin: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (applicationContext as AcmcApp).applicationComponent.inject(this)
        dataLogin = this.getSharedPreferences(
            getString(com.bitassyifaproject.acmc.R.string.sp),
            Context.MODE_PRIVATE
        )




    }
}