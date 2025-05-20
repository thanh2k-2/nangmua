package vn.thanhdz.nangmua.data.model

import com.google.gson.annotations.SerializedName

data class DailyForecast(

    @SerializedName("Date") var date: String,
    @SerializedName("Temperature") var temperature: Temperature,
    @SerializedName("Day") var day: Day,
    @SerializedName("Night") var night: Night,
    @SerializedName("Icon") var icon: Int,
    @SerializedName("Sun") var sun: SunMoon,
    @SerializedName("Moon") var moon: SunMoon
) {
    data class SunMoon(
        @SerializedName("Rise") var rise: String,
        @SerializedName("Set") var set: String
    )

    data class Temperature(
        @SerializedName("Minimum") var minimum: MaxMin,
        @SerializedName("Maximum") var maximum: MaxMin
    ) {
        data class MaxMin(
            @SerializedName("Value") var value: Double,
            @SerializedName("Unit") var unit: String
        )
    }

    data class Day(
        @SerializedName("RainProbability") var rainProbability: Int,
        @SerializedName("SnowProbability") var snowProbability: Int,
        @SerializedName("IceProbability") var iceProbability: Int,
    )

    data class Night(
        @SerializedName("RainProbability") var rainProbability: Int,
        @SerializedName("SnowProbability") var snowProbability: Int,
        @SerializedName("IceProbability") var iceProbability: Int,
    )
}
