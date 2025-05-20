package vn.thanhdz.nangmua.data.model


import com.google.gson.annotations.SerializedName


data class CitySearch(
    @SerializedName("AdministrativeArea")
    var administrativeArea: AdministrativeArea,
    @SerializedName("Country")
    var country: Country,
    @SerializedName("Key")
    var key: String,
    @SerializedName("LocalizedName")
    var localizedName: String,
    @SerializedName("Rank")
    var rank: Int,
    @SerializedName("Type")
    var type: String,
    @SerializedName("Version")
    var version: Int
) {
    data class AdministrativeArea(
        @SerializedName("ID")
        var iD: String,
        @SerializedName("LocalizedName")
        var localizedName: String
    )

    data class Country(
        @SerializedName("ID")
        var iD: String,
        @SerializedName("LocalizedName")
        var localizedName: String
    )
}
