package vn.thanhdz.nangmua.data.repository

import vn.thanhdz.nangmua.data.model.CurrentWeather
import vn.thanhdz.nangmua.data.model.HourlyForecast
import vn.thanhdz.nangmua.data.model.Weather5Day
import vn.thanhdz.nangmua.data.resource.Results

interface WeatherRepository {
    suspend fun load5DayWeather(locationId: Int): Results<Weather5Day>

    suspend fun load12HWeather(locationId: Int): Results<MutableList<HourlyForecast>>

    suspend fun loadCurrentWeather(locationId: Int): Results<MutableList<CurrentWeather>>

}