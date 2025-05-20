package vn.thanhdz.nangmua.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.model.CurrentWeather
import vn.thanhdz.nangmua.data.model.CitySearch
import vn.thanhdz.nangmua.data.repository.WeatherRepository
import vn.thanhdz.nangmua.data.repository.WeatherRepositoryImp
import vn.thanhdz.nangmua.data.resource.Results

class HomeViewModel(
    private var weatherRepository: WeatherRepository = WeatherRepositoryImp()
) : ViewModel() {
    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather
    private val _isLoad: MutableLiveData<Boolean> = MutableLiveData()
    val isLoad: LiveData<Boolean> = _isLoad
    private val _citySearch: MutableLiveData<CitySearch> = MutableLiveData()
    val citySearch: LiveData<CitySearch> = _citySearch


    fun loadDataCurrentWeather(locationId: Int) {
        viewModelScope.launch {
            val result = weatherRepository.loadCurrentWeather(locationId)
            if (result is Results.Success) {
                _currentWeather.postValue(result.data.firstOrNull())
                _isLoad.postValue(true)
            } else {
                _currentWeather.postValue(null)
                _isLoad.postValue(false)
            }
        }
    }

}