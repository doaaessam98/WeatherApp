package com.example.weatherApp.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FragmentSetAllarmBinding
import com.example.weatherApp.databinding.FragmentSettingBinding
import com.example.weatherApp.home.HomeViewModel
import com.example.weatherApp.home.SHARD_NAME
import com.example.weatherApp.main.MainActivity
import com.example.weatherApp.utils.Constants.LANG_UNIT
import com.example.weatherApp.utils.Constants.LOCATION_UNIT
import com.example.weatherApp.utils.Constants.TEMP_UNIT
import com.example.weatherApp.utils.Constants.WIND_UNIT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import java.util.*

@AndroidEntryPoint
class SettingFragment : Fragment() {

    lateinit var binding:FragmentSettingBinding
   private   val settingViewModel:SettingViewModel by viewModels()
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false)
        binding.lifecycleOwner=this
        binding.viewModel=settingViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var lang=sharedPref.getString(LANG_UNIT,"en")
        var tempUnit=sharedPref.getString(TEMP_UNIT,"celsius")
        var windUnit=sharedPref.getString(WIND_UNIT,"metric")
        var locationUnit=sharedPref.getString(LOCATION_UNIT,"gps")
        when (lang) {
            "en" -> binding.btnEnglish.isChecked=true
            "ar" -> binding.btnArabic.isChecked=true
        }
        when (tempUnit) {
            "metric" -> binding.btnCelsius.isChecked=true
            "standard" -> binding.btnKelvin.isChecked=true
            "imperial" -> binding.btnFahrenheit.isChecked=true
        }
        when (locationUnit) {
            "gps" -> binding.btnGps.isChecked=true
            "map" -> binding.btnMap.isChecked=true

        }
        when (windUnit) {
            "metric" -> binding.btnMeter.isChecked=true
            "imperial" -> binding.btnMileHour.isChecked=true
        }

        binding.btnArabic.setOnClickListener {
             changeLang("ar")
        }
    binding.btnEnglish.setOnClickListener {
          changeLang("en")
    }
        binding.btnCelsius.setOnClickListener {
                 changeTempUnit("metric")
        }
        binding.btnFahrenheit.setOnClickListener {
            changeTempUnit("imperial")
        }
        binding.btnKelvin.setOnClickListener {
            changeTempUnit("standard")
        }
        binding.btnMeter.setOnClickListener {
              changeWindUnit("metric")
        }
        binding.btnMileHour.setOnClickListener {
            changeWindUnit("imperial")
        }
        binding.btnMap.setOnClickListener {
              changeLocationUnit("gps")
        }
        binding.btnGps.setOnClickListener {
            changeLocationUnit("map")
        }
    }

    companion object {


    }
    private fun changeLang(lang:String){
        editor.putString(LANG_UNIT,lang)
        editor.commit()
        setLocale(lang)
        settingViewModel.refreshData()
        restartApp()
    }
    private fun changeLocationUnit(unit:String){
        editor.putString(LOCATION_UNIT,unit)
        editor.commit()
        settingViewModel.refreshData()
        restartApp()
    }
    private fun changeWindUnit(unit:String){
        editor.putString(WIND_UNIT,unit)
        editor.commit()
        settingViewModel.refreshData()
        restartApp()
    }
    private fun changeTempUnit(unit:String){
        editor.putString(TEMP_UNIT,unit)
        editor.commit()
        settingViewModel.refreshData()
        restartApp()
    }
    private fun restartApp()
    {
        val intent = Intent(context, MainActivity::class.java)
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity?.finish()
        startActivity(intent)

      //  Runtime.getRuntime().exit(0)

    }
    fun setLocale(languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = requireActivity().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        Locale.setDefault(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        restartApp()
    }

}