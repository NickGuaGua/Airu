package com.guagua.epa.bean

import com.google.gson.annotations.SerializedName

data class AQIBean(
    @SerializedName("sitename")
    val siteName: String,
    @SerializedName("county")
    val county: String,
    @SerializedName("pollutant")
    val pollutant: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("so2")
    val so2: String,
    @SerializedName("co")
    val co: String,
    @SerializedName("o3")
    val o3: String,
    @SerializedName("o3_8hr")
    val o3_8hr: String,
    @SerializedName("pm10")
    val pm10: String,
    @SerializedName("pm2.5")
    val pm2_5: String,
    @SerializedName("no2")
    val no2: String,
    @SerializedName("nox")
    val nox: String,
    @SerializedName("no")
    val no: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_direc")
    val windDirection: String,
    @SerializedName("publishtime")
    val publishTime: String,
    @SerializedName("co_8hr")
    val co_8hr: String,
    @SerializedName("pm2.5_avg")
    val pm2_5Avg: String,
    @SerializedName("pm10_avg")
    val pm10Avg: String,
    @SerializedName("so2_avg")
    val so2Avg: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("siteid")
    val siteId: String,
)