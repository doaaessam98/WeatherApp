package com.example.weatherApp.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FragmentDayDetailsBinding
import com.example.weatherApp.model.Daily
import com.example.weatherApp.utils.Constants
import java.text.SimpleDateFormat
import java.util.*


class DayDetails : Fragment() {
    lateinit var day:Daily
    lateinit var lang: String
    lateinit var unit: String
    lateinit var sharedPref: SharedPreferences
    lateinit var tempUnit: String
    lateinit var binding:FragmentDayDetailsBinding
    lateinit var windSpeedUnit: String
//
   private val  args: DayDetailsArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref =requireActivity().getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)

        lang = sharedPref.getString(Constants.LANG_UNIT, "en").toString()
        unit = sharedPref.getString(Constants.WIND_UNIT, "metric").toString()
        setUnits(unit)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_day_details, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         day = args.day

        updateUi(day)

    }

    private fun updateUi(dayItem:Daily) {

        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(dayItem.dt.toLong() * 1000)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = (calendar.get(Calendar.MONTH) + 1)
        var dayName =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale(lang)).toString()
        var min = dayItem.temp.min.toInt()
        var max =dayItem.temp.max.toInt()
        binding.dayDesc.text=dayItem.weather[0].description
            binding.dayNameDetails.text=dayName
        Glide.with(requireActivity())
            .load("http://openweathermap.org/img/w/" + dayItem!!.weather.get(0).icon + ".png")
            .into(binding.dayImage)

        binding.humidityDay.text=dayItem.humidity.toString()
        binding.pressureDay.text=dayItem.pressure.toString()
        binding.windDay.text=dayItem.windSpeed.toString()
        binding.cloudDay.text=dayItem.clouds.toString()
        binding.sunRiseDay.text=timeFormat(dayItem.sunrise)
        binding.sunSetDay.text=timeFormat(dayItem.sunset)
        binding.dayTem.text = min.toString() + tempUnit + "  /  " + max + tempUnit

//      binding.dayTem.text=dayItem.weather[0].main.toString()

        if (lang.equals("en")) {
            binding.dayTem.text = min.toString() + tempUnit + "  /  " + max + tempUnit

            binding.humidityDay.text = dayItem.humidity.toString() + "%"
            binding.pressureDay.text = dayItem.pressure.toString() + " hPa"
            binding.windDay.text = dayItem.windSpeed.toString() + " " + windSpeedUnit
//            binding.dayTem.text = (dayItem.weather[0].main.toInt()).toString() + tempUnit
            binding.cloudDay.text = dayItem.clouds.toString()

        } else {
            binding.cloudDay.text = convertToArabic(dayItem.clouds)
            binding.humidityDay.text = convertToArabic(dayItem.humidity) + "%"
            binding.pressureDay.text = convertToArabic(dayItem.pressure) + "hPa"
            binding.windDay.text =
                convertToArabic(dayItem.windSpeed.toInt()) + windSpeedUnit
            //binding.dayTem.text = convertToArabic(dayItem.temp.toInt()) + tempUnit

            binding.dayTem.text= convertToArabic(max) + tempUnit + " /" + convertToArabic(min) + tempUnit
        }
    }

    companion object {
    }

    private fun timeFormat(millisSeconds: Int): String {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis((millisSeconds * 1000).toLong())
        val format = SimpleDateFormat("hh:mm aaa", Locale(lang.toString()))
        return format.format(calendar.time)

    }

    fun convertToArabic(value: Int): String? {
        return (value.toString() + "")
            .replace("1", "١").replace("2", "٢")
            .replace("3", "٣").replace("4", "٤")
            .replace("5", "٥").replace("6", "٦")
            .replace("7", "٧").replace("8", "٨")
            .replace("9", "٩").replace("0", "٠")
    }

    fun setUnits(unit: String) {
        when (unit) {
            "metric" -> {
                tempUnit = "°c"
                windSpeedUnit = "m/s"
            }
            "imperial" -> {
                tempUnit = "°f"
                windSpeedUnit = "m/h"
            }
            "standard" -> {
                tempUnit = "°k"
                windSpeedUnit = "m/s"
            }

        }
    }

        }


