package com.example.weatherApp.map

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weatherApp.R
import com.example.weatherApp.alarm.setAlarmDialog.SetAlarmsFragment
import com.example.weatherApp.favorite.FavouriteModel
import com.example.weatherApp.home.LOCAION_LOG
import com.example.weatherApp.home.LOCATION_LAT
import com.example.weatherApp.home.SHARD_NAME
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MapFragment : Fragment() {
    private var mapId: String ?= null
    var userLocationMarker: Marker? = null
     lateinit var btn_float: FloatingActionButton
    lateinit var root:View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var latitude: Double = 0.0
    var longitude: Double = 0.0
     var timeZone:String=""
    lateinit var navControler: NavController
    private val mapViewModel: MapViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            latitude = it.latitude
            longitude = it.longitude


        }




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      root= inflater.inflate(R.layout.fragment_map, container, false)
       btn_float=root.findViewById(R.id.add_flout_btn)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapId = arguments?.getString("mapId")!!
        navControler = Navigation.findNavController(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        sharedPreferences = requireActivity().getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        btn_float.setOnClickListener {

                if(mapId.equals("hom")){
                    editor.putString(LOCATION_LAT, latitude.toString())
                    editor.putString(LOCAION_LOG, longitude.toString())
                    editor.commit()
                    navControler.navigate(R.id.homeFragment)



                }
                else if (mapId.equals("fav")){
                    getTimeZone(latitude,longitude)
                    var newFavPlace=FavouriteModel(timeZone,latitude,longitude)
                    mapViewModel.insertFavPlaceInDataBase(newFavPlace)

                    navControler.navigate(R.id.favoriteFragment)


            }
            else if (mapId.equals("alarm")){
                     getTimeZone(latitude,longitude)

                    if(timeZone.isNullOrEmpty()){
                        Toast.makeText(requireContext()," try again", Toast.LENGTH_LONG).show()

                    }
                    else {
                        val bundle = Bundle()
                        bundle.putString("lat", latitude.toString())
                        bundle.putString("lon", longitude.toString())
                        bundle.putString("timeZone", timeZone)
                        var alarm = SetAlarmsFragment()
                        alarm.arguments = bundle
                        navControler.navigate(R.id.setAlarmsFragment, bundle)
                    }
            }
        }

    }
     private fun getTimeZone(lat:Double,lon:Double) {
         val geocoder: Geocoder
         val addresses: List<Address>?
         geocoder = Geocoder(context, Locale.getDefault())

         try {
             addresses = geocoder.getFromLocation(lat, lon, 1)
             if (addresses != null) {
                 val address = addresses[0]
                 val city = address.locality
                 val state = address.adminArea
                 val country = address.countryName
      timeZone= "$state $city $country".trimIndent()


             }

         }
         catch (e: IOException) {

         }

     }
}