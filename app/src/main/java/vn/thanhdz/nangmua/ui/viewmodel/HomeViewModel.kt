package vn.thanhdz.nangmua.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.model.CurrentWeather
import vn.thanhdz.nangmua.data.model.Cur
import vn.thanhdz.nangmua.data.repository.WeatherRepository
import vn.thanhdz.nangmua.data.repository.WeatherRepositoryImp
import vn.thanhdz.nangmua.data.resource.Results

class HomeViewModel(
    private var weatherRepository: WeatherRepository = WeatherRepositoryImp(),

    ) : ViewModel() {
    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather


    fun loadDataCurrentWeather(locationId: Int) {
        viewModelScope.launch {
            val result = weatherRepository.loadCurrentWeather(locationId)
            if (result is Results.Success) {
                _currentWeather.postValue(result.data.firstOrNull())
            } else {
                _currentWeather.postValue(null)
            }
        }
    }


}
