package com.example.weatherApp.favorite

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FavouriteItemBinding
import com.example.weatherApp.home.LOCAION_LOG
import com.example.weatherApp.home.LOCATION_LAT
import com.example.weatherApp.home.SHARD_NAME
import com.example.weatherApp.model.WeatherResponse

class FavouriteAdapter(var onFavMenuClick: OnFavMenuClick,var favouriteList:ArrayList<FavouriteModel>,var context:Context) : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    lateinit var prefs: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    init {
        prefs  =context.getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)

        editor= prefs.edit()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        var binding=FavouriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        var fav_item=favouriteList[position]
        holder.view.countryFavName.text=fav_item.timeZone
        holder.view.moreFav.setOnClickListener {

            onFavMenuClick.onClick(holder.view.moreFav,fav_item.timeZone!!)
        }
        holder.view.favLayout.setOnClickListener {
            editor.putString(LOCATION_LAT,fav_item.lat.toString())
            editor.putString(LOCAION_LOG,fav_item.lon.toString())
            editor.commit()
            Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_homeFragment);

        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    fun setData(it:List<FavouriteModel>) {

            favouriteList.clear()
            favouriteList.addAll(it)
            notifyDataSetChanged()


    }

    class FavouriteViewHolder(var view:FavouriteItemBinding):RecyclerView.ViewHolder(view.root) {

    }


}