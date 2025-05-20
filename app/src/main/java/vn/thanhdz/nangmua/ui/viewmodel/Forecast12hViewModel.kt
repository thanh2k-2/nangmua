package vn.thanhdz.nangmua.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.model.HourlyForecast
import vn.thanhdz.nangmua.data.repository.WeatherRepository
import vn.thanhdz.nangmua.data.repository.WeatherRepositoryImp
import vn.thanhdz.nangmua.data.resource.Results

class Forecast12hViewModel(

    private val weatherRepository: WeatherRepository = WeatherRepositoryImp()

) : ViewModel() {

    private var _weathers: MutableLiveData<MutableList<HourlyForecast>> = MutableLiveData()

    val weather: LiveData<MutableList<HourlyForecast>> = _weathers

    fun loadDataForecast12h(locationId: Int) {

        viewModelScope.launch {
            val results = weatherRepository.load12HWeather(locationId)
            if (results is Results.Success) {
                _weathers.postValue(results.data)
            } else {
                _weathers.postValue(null)
            }
        }
    }

    fun get(): LiveData<MutableList<HourlyForecast>> {
        return _weathers
    }
}