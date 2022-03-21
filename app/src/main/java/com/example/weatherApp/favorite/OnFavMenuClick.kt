package com.example.weatherApp.favorite

import android.service.autofill.OnClickAction
import android.view.View

interface OnFavMenuClick {
    fun onClick(view: View,timezone:String)

}