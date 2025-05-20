package vn.thanhdz.nangmua.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_table")
data class Loc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "key") val key: Int = 0,
    @ColumnInfo(name = "locationName") val locationName: String,
    @ColumnInfo(name = "country") val country : String?
)
