package com.example.weatherApp.dilog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.weatherApp.R
import com.example.weatherApp.databinding.ActivityUserDialogBinding
import com.example.weatherApp.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDialogActivity : AppCompatActivity() {
    private val dialogViewModel:UserDialogViewModel  by viewModels()
    lateinit var  binding : ActivityUserDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=DataBindingUtil.setContentView(this, R.layout.activity_user_dialog)
        binding.lifecycleOwner=this
        binding.viewModel=dialogViewModel
        binding.btnOk.setOnClickListener {
            if(dialogViewModel.isGps.get()){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if (dialogViewModel.isMap.get()){
                // open map fragment
                println("map")
            }
            else{

                println("no")
            }
        }
    }

}