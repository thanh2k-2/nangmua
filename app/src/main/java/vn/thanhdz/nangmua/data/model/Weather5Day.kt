package vn.thanhdz.nangmua.data.model

import com.google.gson.annotations.SerializedName

data class Weather5Day(
    @SerializedName("DailyForecasts") var dailyForecasts: MutableList<DailyForecast>
)
