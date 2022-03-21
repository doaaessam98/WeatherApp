package com.example.weatherApp.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    @SerializedName("moonrise")
    val moonrise: Int,
    @SerializedName("moonset")
    val moonset: Int,
//    @SerializedName("pop")
//    val pop: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("weather")
    val weather: List<WeatherX>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        TODO("feelsLike"),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        TODO("temp"),
        parcel.readDouble(),
        TODO("weather"),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clouds)
        parcel.writeDouble(dewPoint)
        parcel.writeInt(dt)
        parcel.writeInt(humidity)
        parcel.writeDouble(moonPhase)
        parcel.writeInt(moonrise)
        parcel.writeInt(moonset)
        parcel.writeInt(pressure)
        parcel.writeDouble(rain)
        parcel.writeInt(sunrise)
        parcel.writeInt(sunset)
        parcel.writeDouble(uvi)
        parcel.writeInt(windDeg)
        parcel.writeDouble(windGust)
        parcel.writeDouble(windSpeed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Daily> {
        override fun createFromParcel(parcel: Parcel): Daily {
            return Daily(parcel)
        }

        override fun newArray(size: Int): Array<Daily?> {
            return arrayOfNulls(size)
        }
    }
}