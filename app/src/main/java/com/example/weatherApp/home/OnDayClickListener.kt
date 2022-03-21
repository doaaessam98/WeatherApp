package com.example.weatherApp.home

import com.example.weatherApp.model.Daily

interface OnDayClickListener {
   fun onDayClicked(day:Daily)
}