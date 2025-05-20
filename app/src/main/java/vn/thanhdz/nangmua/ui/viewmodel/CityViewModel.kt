package vn.thanhdz.nangmua.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.thanhdz.nangmua.data.model.CitySearch
import vn.thanhdz.nangmua.data.repository.LocationRepository
import vn.thanhdz.nangmua.data.repository.LocationRepositoryImp
import vn.thanhdz.nangmua.data.resource.Results

class CityViewModel(

    private val locationRepository: LocationRepository = LocationRepositoryImp()
) : ViewModel() {

    private var _cities: MutableLiveData<MutableList<CitySearch>> = MutableLiveData()
    val citySearch: LiveData<MutableList<CitySearch>> = _cities
    fun loadLocationData(query: String) {
        viewModelScope.launch {
            val result = locationRepository.getLocation(query)
            if (result is Results.Success) {
                _cities.postValue(result.data)
            } else {
                _cities.postValue(null)
            }
        }
    }

}