package vn.thanhdz.nangmua.data.model

import com.google.gson.annotations.SerializedName
import vn.thanhdz.nangmua.data.model.CurrentWeather.RealFeelTemperature.MetricValue

data class CurrentWeather(
    @SerializedName("LocalObservationDateTime") val dateTime: String,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("Temperature") val temperature: Temperature, // nhiệt độ
    @SerializedName("RealFeelTemperature") val realFeelTemperature: RealFeelTemperature, // nhiệt  dộ thực tế.
    @SerializedName("Wind") val wind: Wind, // gió
    @SerializedName("UVIndex") val uVIndex: Int,// chỉ số UV
    @SerializedName("WeatherIcon") val weatherIcon : Int,
    @SerializedName("UVIndexText") val uVIndexText: String, //thông tin UV.
    @SerializedName("CloudCover") val cloudCover: Int, // mây.
    @SerializedName("RelativeHumidity") val relativeHumidity: Int, // độ ẩm.
    @SerializedName("PrecipitationSummary") val precipitationSummary: PrecipitationSummary, //Tóm tắt lượng mưa
    @SerializedName("Visibility") val visibility: Visibility, // tầm nhìn.
    @SerializedName("Pressure") val pressure: Pressure // áp suất.
) {

    data class Pressure(
        @SerializedName("Metric") val metric: MetricValue,
    )

    data class Visibility(
        @SerializedName("Metric") val metric: MetricValue,
    )

    data class Temperature(
        @SerializedName("Metric") val metric: MetricValue,
    ) {
        data class MetricValue(
            @SerializedName("Value") val value: Double,
            @SerializedName("Unit") val unit: String,
            @SerializedName("UnitType") val unitType: Int? = null
        )
    }

    data class RealFeelTemperature(
        @SerializedName("Metric") val metric: MetricValue,
    ) {
        data class MetricValue(
            @SerializedName("Value") val value: Double,
            @SerializedName("Unit") val unit: String,
            @SerializedName("UnitType") val unitType: Int? = null,
            @SerializedName("Phrase") val phrase: String? = null
        )
    }

    data class Wind(
        @SerializedName("Speed") val speed: Speed, //tốc độ gió.
        @SerializedName("Direction") val direction: Direction // hướng gió.
    ) {
        data class Speed(
            @SerializedName("Metric") val metric: MetricValue,
        ) {
            data class MetricValue(
                @SerializedName("Value") val value: Double,
                @SerializedName("Unit") val unit: String,
                @SerializedName("UnitType") val unitType: Int? = null
            )
        }

        data class Direction(
            @SerializedName("Degrees") val degrees: Int,
            @SerializedName("Localized") val localized: String,
            @SerializedName("English") val english: String? = null
        )
    }

    data class PrecipitationSummary(
        @SerializedName("Precipitation") val precipitation: Precipitation
    ) {
        data class Precipitation(
            @SerializedName("Metric") val metric: MetricValue,
        ) {
            data class MetricValue(
                @SerializedName("Value") val value: Double,
                @SerializedName("Unit") val unit: String,
                @SerializedName("UnitType") val unitType: Int? = null
            )
        }
    }

}