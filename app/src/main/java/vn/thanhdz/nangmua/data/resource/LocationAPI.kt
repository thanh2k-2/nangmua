package vn.thanhdz.nangmua.data.resource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import vn.thanhdz.nangmua.data.model.CitySearch

interface LocationAPI {
    @GET("locations/v1/cities/autocomplete")
    suspend fun getLocations(
        @Query("q") query: String,
        @Query("apikey") apiKey: String,
        @Query("language") language: String,
    ): Response<MutableList<CitySearch>>
}