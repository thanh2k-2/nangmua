package vn.thanhdz.nangmua.data.model

import com.google.gson.annotations.SerializedName

data class HourlyForecast(
    @SerializedName("DateTime") var date: String,
    @SerializedName("Temperature") var temperature: Temperature,
    @SerializedName("RainProbability") var rainProbability: Int,
    @SerializedName("SnowProbability") var snowProbability: Int,
    @SerializedName("WeatherIcon") var weatherIcon: Int,
    @SerializedName("IceProbability") var iceProbability: Int,

    ) {
    data class Temperature(
        @SerializedName("Value") var value: Double,
        @SerializedName("Unit") var unit: String
    )

    data class RealFeelTemperature(
        @SerializedName("Value") val value: Double,
        @SerializedName("Unit") val unit: String,
        @SerializedName("Phrase") val phrase: String
    )

    data class Wind(
        @SerializedName("Speed") val speed: WindSpeed,
        @SerializedName("Direction") val direction: WindDirection
    ) {
        data class WindSpeed(
            @SerializedName("Value") val value: Double,
            @SerializedName("Unit") val unit: String
        )

        data class WindDirection(
            @SerializedName("Degrees") val degrees: Int,
            @SerializedName("Localized") val localized: String
        )
    }

}
