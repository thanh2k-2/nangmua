package vn.thanhdz.nangmua.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.thanhdz.nangmua.data.dao.LocDao
import vn.thanhdz.nangmua.data.model.Loc

@Database(entities = [Loc::class], version = 6, exportSchema = false)
abstract class LocRoomDatabase : RoomDatabase() {

    abstract fun locDao(): LocDao
    companion object {
        private var INSTANCE: LocRoomDatabase? = null
        private const val DB_NAME = "thanh_db"

        fun getDataBase(context: Context): LocRoomDatabase {
            return INSTANCE?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    LocRoomDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}
