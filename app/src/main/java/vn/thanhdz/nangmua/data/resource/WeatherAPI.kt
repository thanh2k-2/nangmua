package vn.thanhdz.nangmua.data.resource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vn.thanhdz.nangmua.data.model.CurrentWeather
import vn.thanhdz.nangmua.data.model.HourlyForecast
import vn.thanhdz.nangmua.data.model.Weather5Day

interface WeatherAPI {


    @GET("forecasts/v1/daily/5day/{locationId}")
    suspend fun get5DayWeatherForecast(
        @Path("locationId") locationId: Int,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean,
    ): Response<Weather5Day>

    @GET("forecasts/v1/hourly/12hour/{locationId}")
    suspend fun get12HWeatherForecast(
        @Path("locationId") locationId: Int,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean ,
        @Query("metric") metric: Boolean ,
    ): Response<MutableList<HourlyForecast>>

    @GET("currentconditions/v1/{locationId}")
    suspend fun getCurrentWeather(
        @Path("locationId") locationId: Int,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
        @Query("details") details: Boolean,
        @Query("metric") metric: Boolean,
    ): Response<MutableList<CurrentWeather>>
}