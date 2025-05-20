package vn.thanhdz.nangmua.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.thanhdz.nangmua.data.model.CitySearch
import vn.thanhdz.nangmua.data.resource.LocationAPI
import vn.thanhdz.nangmua.data.resource.Results
import vn.thanhdz.nangmua.data.resource.RetrofitHelper

class LocationRepositoryImp : LocationRepository {
    override suspend fun getLocation(
        query: String
    ): Results<MutableList<CitySearch>> {
        return withContext(Dispatchers.IO) {
            val retrofit = RetrofitHelper.getInstance().create(LocationAPI::class.java)
            val response = retrofit.getLocations(query, WeatherRepositoryImp.API_KEY, "vi")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Results.Success(body)
                } else {
                    Results.Error(Exception("Response body is null"))
                }
            } else {
                Results.Error(Exception(response.message()))
            }
        }
    }
}