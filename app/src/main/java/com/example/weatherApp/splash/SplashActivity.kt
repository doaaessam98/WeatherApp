package com.example.weatherApp.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherApp.R
import com.example.weatherApp.dilog.UserDialogActivity
import com.example.weatherApp.home.HomeFragment
import com.example.weatherApp.home.LOCAION_LOG
import com.example.weatherApp.home.LOCATION_LAT
import com.example.weatherApp.home.SHARD_NAME
import com.example.weatherApp.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences =this.getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)
        editor= sharedPreferences.edit()
        Handler(Looper.myLooper()!!).postDelayed({
            if (sharedPreferences.getString(LOCATION_LAT, "").isNullOrEmpty()||sharedPreferences.getString(
                    LOCAION_LOG, "").isNullOrEmpty()) {
                val intent = Intent(this, UserDialogActivity::class.java)
                startActivity(intent)
                finish()
             }
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
           }

        }, 2000)
    }
}