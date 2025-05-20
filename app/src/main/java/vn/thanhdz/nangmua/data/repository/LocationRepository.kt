package vn.thanhdz.nangmua.data.repository

import vn.thanhdz.nangmua.data.model.CitySearch
import vn.thanhdz.nangmua.data.resource.Results

interface LocationRepository {

    suspend fun getLocation(query: String): Results<MutableList<CitySearch>>
}