package com.bitassyifaproject.acmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bitassyifaproject.acmc.auth.ui.LoginActivity
import android.view.WindowManager

import android.os.Build
import android.view.Window


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent( this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}