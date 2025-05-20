package vn.thanhdz.nangmua.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import vn.thanhdz.nangmua.data.model.Loc

@Dao
interface LocDao {
    @Query("SELECT * FROM location_table WHERE `key` == :locationId")
    suspend fun find(locationId: Int) : Loc?

    @Query("SELECT * FROM location_table")
    suspend fun getAllLocation(): MutableList<Loc>?

    @Insert
    suspend fun insert(loc: Loc)

    @Delete
    suspend fun delete(loc: Loc)
}