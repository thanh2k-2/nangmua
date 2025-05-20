package vn.thanhdz.nangmua.data.repository

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateTimeConverter {

    fun toTime(strDateTime: String): String {
        return try {
            val dateTime = LocalDateTime.parse(strDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            val datePart = dateTime.format(
                DateTimeFormatter.ofPattern("EEEE, d 'tháng' M", Locale("vi", "VN"))
            )
            val timePart = dateTime.format(
                DateTimeFormatter.ofPattern("H 'giờ'", Locale("vi", "VN"))
            )

           timePart
        } catch (e: Exception) {
            e.printStackTrace()
            "Không thể hiển thị thời gian" // Fallback khi có lỗi
        }
    }
    fun toDate(strDateTime: String): String {
        return try {
            val dateTime = LocalDate.parse(strDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            val datePart = dateTime.format(
                DateTimeFormatter.ofPattern("EEEE", Locale("vi", "VN"))
            )
            datePart
        } catch (e: Exception) {
            e.printStackTrace()
            "Không thể hiển thị thời gian" // Fallback khi có lỗi
        }
    }

    fun toDateTime(strDateTime: String): String {
        return try {
            val dateTime = LocalDate.parse(strDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            val datePart = dateTime.format(
                DateTimeFormatter.ofPattern("EEEE, d 'tháng' M", Locale("vi", "VN"))
            )
            datePart
        } catch (e: Exception) {
            e.printStackTrace()
            "Không thể hiển thị thời gian" // Fallback khi có lỗi
        }
    }
}