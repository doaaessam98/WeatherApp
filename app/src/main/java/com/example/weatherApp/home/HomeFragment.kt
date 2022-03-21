package com.example.weatherApp.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FragmentHomeBinding
import com.example.weatherApp.map.MapFragment
import com.example.weatherApp.model.Daily
import com.example.weatherApp.model.WeatherResponse
import com.example.weatherApp.utils.Constants
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val LOCATION_LAT="lat"
const val LOCAION_LOG="lon"
const val SHARD_NAME="shard"
@AndroidEntryPoint
class HomeFragment : Fragment(),OnDayClickListener {
    private val PERMISSION_ID = 55
    lateinit var navController:NavController
    lateinit var lang: String
    lateinit var unit: String
    lateinit var tempUnit: String
    lateinit var windSpeedUnit: String
    lateinit var action:NavDirections
    lateinit var job:Job
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var locationLat:Double=0.0
    var locationLon:Double=0.0

    lateinit var binding: FragmentHomeBinding
    lateinit var hourlyAdapter:HourAdapter
    lateinit var dailyAdapter:DailyAdapter
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
        private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())

        sharedPref = requireActivity().getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        firstTime()
        lang = sharedPref.getString(Constants.LANG_UNIT, "en").toString()
        unit = sharedPref.getString(Constants.WIND_UNIT,"metric").toString()
        setLocale(lang)
        setUnits(unit)
        //getLastLocation()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.lifecycleOwner=this
        binding.homeModel=homeViewModel
        hourlyAdapter= HourAdapter(arrayListOf(),requireContext())
        dailyAdapter= DailyAdapter(arrayListOf(),requireContext(),this)
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        if (sharedPref.getString(LOCATION_LAT,"").isNullOrEmpty()||sharedPref.getString(LOCAION_LOG,"").isNullOrEmpty()) {
              binding.progress.visibility=VISIBLE
            binding.weatherCard.visibility = View.GONE
            binding.weatherLastCard.visibility = View.GONE
            binding.location.visibility=View.GONE
            binding.editLocationLinear.visibility=View.GONE
            binding.hourText.text = null
            //getLastLocation()
        } else {

            homeViewModel.getDataFromDataBase(sharedPref.getString(LOCATION_LAT,"")!!.toDouble(),sharedPref.getString(
                LOCAION_LOG,"")!!.toDouble())
                  homeViewModel.getWeather
                    .observe(viewLifecycleOwner, Observer {
              if (it!=null) {

                  updateUi(it)
              }
                        else{





              }
                    })

        }

    return binding.root

    }
//////////
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HourlyRecyclerSetUp()
         dailyRecyclerSetUp()
     navController= Navigation.findNavController(view)

    binding.editLocationLinear.setOnClickListener {
        val bundle= Bundle()
        bundle.putString("mapId","hom")
        val mapFragment= MapFragment()
        mapFragment.arguments=bundle
        navController.navigate(R.id.mapFragment,bundle);
    }

    }

    private fun dailyRecyclerSetUp() {
        binding.daysRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL ,false)
          adapter=dailyAdapter
        }

    }

    private fun HourlyRecyclerSetUp() {
      binding.hoursRecycler.apply {
          setHasFixedSize(true)
          layoutManager = LinearLayoutManager(
              requireContext(), LinearLayoutManager.HORIZONTAL
              ,false)
          adapter=hourlyAdapter
      }
    }

    companion object {


    }


    private fun updateUi(weather:WeatherResponse) {
        binding.progress.visibility=View.GONE
        binding.weatherCard.visibility = View.VISIBLE
        binding.weatherLastCard.visibility = View.VISIBLE
        binding.editLocationLinear.visibility= VISIBLE
        binding.location.visibility=View.VISIBLE
          weather.let {
              weather.apply {
                  binding.location.text=convertTimezone(it)
                  binding.st.text=it.current.weather[0].description

                  Glide.with(requireActivity())
                      .load("http://openweathermap.org/img/w/" + current!!.weather.get(0).icon + ".png")
                      .into(binding.statu)

                  binding.tem.text=it.current.temp.toString()+"°"
                  binding.humidityHome.text=current.humidity.toString()
                  binding.pressureHome.text=current.pressure.toString()
                  binding.windHome.text=current.windSpeed.toString()
                  binding.cloudHome.text=current.clouds.toString()
                  binding.sunRiseHome.text=timeFormat(it.current.sunrise)
                  binding.sunSetHome.text=timeFormat(it.current.sunset)
                  //binding.date.text = dateFormat(it.current.dt)


                  dailyAdapter.updateData(daily)
                  hourlyAdapter.updateData(hourly)



                  if (lang.equals("en")) {
                      binding.humidityHome.text = it.current.humidity.toString() + "%"
                      binding.pressureHome.text = it.current.pressure.toString() + " hPa"
                      binding.windHome.text = it.current.windSpeed.toString() + " " + windSpeedUnit
                      binding.tem.text = (it.current.temp.toInt()).toString() + tempUnit
                      binding.cloudHome.text = it.current.clouds.toString()

                  } else {
                      binding.cloudHome.text = convertToArabic(it.current.clouds)
                      binding.humidityHome.text = convertToArabic(it.current.humidity) + "%"
                      binding.pressureHome.text = convertToArabic(it.current.pressure) + "hPa"
                      binding.windHome.text =
                          convertToArabic(it.current.windSpeed.toInt()) + windSpeedUnit
                      binding.tem.text = convertToArabic(it.current.temp.toInt()) + tempUnit


                  }
              }
          }
    }

    private fun dateFormat(milliSeconds: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds.toLong() * 1000)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var year = calendar.get(Calendar.YEAR)
        if (lang.equals("en")) {
            return day.toString() + "/" + month + "/" + year
        } else {
            return convertToArabic(day) + "/" + convertToArabic(month) + "/" + convertToArabic(year)
        }

    }
    private fun timeFormat(millisSeconds: Int): String {
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis((millisSeconds * 1000).toLong())
        if (lang.equals("en")) {
            val format = SimpleDateFormat("hh:00 aaa")
            return format.format(calendar.time)
        } else {
            val format = SimpleDateFormat("۰۰:hh aaa")
            return format.format(calendar.time)
        }
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

    override fun onDayClicked(day: Daily) {
        var hom=HomeFragment()
       var actoion=HomeFragmentDirections.actionHomeFragmentToDayDetails(day)
     findNavController().navigate(actoion)

    }

    fun setLocale(languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = requireActivity().resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun convertToArabic(value: Int): String? {
        return (value.toString() + "")
            .replace("1", "١").replace("2", "٢")
            .replace("3", "٣").replace("4", "٤")
            .replace("5", "٥").replace("6", "٦")
            .replace("7", "٧").replace("8", "٨")
            .replace("9", "٩").replace("0", "٠")
    }

    fun convertTimezone(weatherResponse: WeatherResponse): String {
        var arabicTimezone = ""
        var addressList: List<Address>? = null

        val geocoder = Geocoder(context, Locale(lang))
        try {
            addressList = geocoder.getFromLocation(weatherResponse.lat, weatherResponse.lon, 1)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addressList?.size!! > 0) {
            val address = addressList!![0]

            arabicTimezone=address.adminArea
        }
        return arabicTimezone
    }
    private fun firstTime() {
        var isFirst = sharedPref.getBoolean("isFirstTimeLaunch", true)
        if (isFirst) {

            editor.putString(
                Constants.LANG_UNIT, "en")
            editor.putString(Constants.WIND_UNIT, "metric")
            editor.putBoolean("isFirstTimeLaunch", false)
            editor.commit()
            getLastLocation()
        }
    }

    private fun getCurrentTime():String{
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        return currentDateAndTime
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
       if(checkPermissions()){
           if(isLocationEnabled()){

                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                   val location: Location? = task.result
                   if (location == null) {
                       requestNewLocationData()
                   } else {

                       locationLat= location?.latitude!!
                       locationLon = location?.longitude!!
                       editor.putString(LOCATION_LAT,locationLat.toString())
                       editor.putString(LOCAION_LOG,locationLon.toString())
                       editor.commit()


                       homeViewModel.getDataFromDataBase(sharedPref.getString(LOCATION_LAT,"")!!.toDouble(),sharedPref.getString(
                           LOCAION_LOG,"")!!.toDouble())
                       homeViewModel.getWeather
                           .observe(viewLifecycleOwner, Observer {
                               if (it != null) {

                                   updateUi(it)
                               }
                           })




                   }
               }
           }
           else {
               val firstTimeLaunch = sharedPref.getBoolean("isFirstTimeLaunch", true)
               if (firstTimeLaunch) {
                   val firstTimeLocationEnabled =
                       sharedPref.getBoolean("isFirstTimeLocationEnabled", true)
                   if (firstTimeLocationEnabled) {

                   }
               }
               locationNotEnable()
           }
       }
       else {
           requestPermissions()
       }

       }
    private fun locationNotEnable() {

        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        ActivityCompat.startActivityForResult(requireActivity(), intent, PERMISSION_ID, Bundle())
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
      if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)){
          editor.putBoolean("isFirstTimeLocationEnabled", true)
          return true
      }

         else {
            return false;
        }
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
//
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_ID
        )
    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        Looper.myLooper()?.let {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                it
            )
        }
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            locationLat= mLastLocation?.latitude!!
            locationLon= mLastLocation?.longitude!!

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.e(TAG, "onRequestPermissionsResult: ")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

        }

