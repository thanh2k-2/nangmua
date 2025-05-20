package vn.thanhdz.nangmua.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.thanhdz.nangmua.data.model.CurrentWeather
import vn.thanhdz.nangmua.data.model.HourlyForecast
import vn.thanhdz.nangmua.data.model.Weather5Day
import vn.thanhdz.nangmua.data.resource.Results
import vn.thanhdz.nangmua.data.resource.RetrofitHelper
import vn.thanhdz.nangmua.data.resource.WeatherAPI

class WeatherRepositoryImp : WeatherRepository {

    companion object {
        const val API_KEY4 = "r0EpEDxy45uzDzRDQ79GQbDuG7vwQHQF"
        const val API_KEY1 = "Tmpl2G2l9zsPaU4K0IGsOR0WsPVEMzPI"
        const val API_KEY = "SwK0Yvm3J1Apwn05HeHPEjUHDLl5Lo7W"
        const val API_KEY3 = "PElb1KAMPLGJsqYcR1I69VygZrxZEdCU"
        const val API_KEY5 = "PAFO9SEYw0v6MVcPyosbTC3uq0lrBtaD"
        const val API_KEY2 = "rhUESetZhaG3t1BknY4LjsQ87GkAcqgt"
    }

    override suspend fun load5DayWeather(
        locationId: Int
    ): Results<Weather5Day> {
        return withContext(Dispatchers.IO) {
            val weatherAPI = RetrofitHelper.getInstance().create(WeatherAPI::class.java)
            val response = weatherAPI.get5DayWeatherForecast(
                locationId, API_KEY, "vi", details = true,
                metric = true
            )
            if (response.isSuccessful) {
                Results.Success(response.body()!!)
            } else {
                Results.Error(Exception(response.message()))
            }
        }
    }

    override suspend fun load12HWeather(
        locationId: Int
    ): Results<MutableList<HourlyForecast>> {

        return withContext(Dispatchers.IO) {
            val weatherAPI = RetrofitHelper.getInstance().create(WeatherAPI::class.java)
            val response = weatherAPI.get12HWeatherForecast(
                locationId, API_KEY,
                "vi",
                details = true,
                metric = true
            )
            if (response.isSuccessful) {
                Results.Success(response.body()!!)
            } else {
                Results.Error(Exception(response.message()))
            }
        }
    }

    override suspend fun loadCurrentWeather(
        locationId: Int
    ): Results<MutableList<CurrentWeather>> {
        return withContext(Dispatchers.IO) {
            val weatherAPI = RetrofitHelper.getInstance().create(WeatherAPI::class.java)
            val response = weatherAPI.getCurrentWeather(
                locationId, API_KEY, "vi", details = true,
                metric = true
            )
            if (response.isSuccessful) {
                Results.Success(response.body()!!)
            } else {
                Results.Error(Exception(response.message()))
            }
        }
    }


}