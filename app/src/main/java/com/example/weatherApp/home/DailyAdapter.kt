package com.example.weatherApp.home

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherApp.R
import com.example.weatherApp.databinding.DayItemBinding
import com.example.weatherApp.model.Daily
import com.example.weatherApp.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(var dailyList: ArrayList<Daily>,var context: Context,var onDayClick: OnDayClickListener) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {



    lateinit var lang: String
    lateinit var unit: String
    lateinit var sharedPref: SharedPreferences
    lateinit var tempUnit: String
    init{
        sharedPref =context.getSharedPreferences(SHARD_NAME, Context.MODE_PRIVATE)

        lang = sharedPref.getString(Constants.LANG_UNIT, "en").toString()
        unit = sharedPref.getString(Constants.WIND_UNIT, "metric").toString()
        setUnits(unit)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
      var binding=DayItemBinding.inflate(LayoutInflater
          .from(parent.context),parent,false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item=dailyList[position]
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(dailyList[position].dt.toLong()*1000)
        holder.view.dayName.text=getDayName(calendar.getDisplayName(
            Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()),context )
        holder.view.rootView.setOnClickListener {
                 onDayClick.onDayClicked(item)
       }
    }
    fun getDayName(dayName:String,context:Context):String{
        return when (dayName.trim()) {
            "Saturday" ->context.getString(R.string.saturday)
            "Sunday" ->context.getString(R.string.sunday)
            "Monday" ->context.getString(R.string.monday)
            "Tuesday" ->context.getString(R.string.tuesday)
            "Wednesday" ->context.getString(R.string.wednesday)
            "Friday" ->context.getString(R.string.friday)
            "Thursday" ->context.getString(R.string.thursday)
            else ->dayName
        }
    }
    fun updateData(newDailyList: List<Daily>){
        dailyList.clear()
        dailyList.addAll(newDailyList)
        notifyDataSetChanged()
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
            }
            "imperial" -> {
                tempUnit = "°f"
            }
            "standard" -> {
                tempUnit = "°k"
            }

        }
    }

    override fun getItemCount(): Int {
     return dailyList.size
    }

    class DailyViewHolder(var view:DayItemBinding) :RecyclerView.ViewHolder(view.root){



    }
}