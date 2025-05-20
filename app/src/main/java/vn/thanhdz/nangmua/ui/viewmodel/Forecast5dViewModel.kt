package vn.thanhdz.nangmua.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.model.DailyForecast
import vn.thanhdz.nangmua.data.repository.WeatherRepository
import vn.thanhdz.nangmua.data.repository.WeatherRepositoryImp
import vn.thanhdz.nangmua.data.resource.Results

class Forecast5dViewModel(
    private var weatherRepository: WeatherRepository = WeatherRepositoryImp()
) : ViewModel() {


    private var _weather5d: MutableLiveData<MutableList<DailyForecast>> = MutableLiveData()
    val weather: LiveData<MutableList<DailyForecast>> = _weather5d

    fun loadDataForecast5d(locationId: Int) {
        viewModelScope.launch {
            val result = weatherRepository.load5DayWeather(locationId)
            if (result is Results.Success) {
                _weather5d.postValue(result.data.dailyForecasts)
            } else {
                _weather5d.postValue(null)
            }
        }
    }

    fun get(): LiveData<MutableList<DailyForecast>> {
        return _weather5d
    }


}