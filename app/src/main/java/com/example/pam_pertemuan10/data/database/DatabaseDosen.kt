package com.example.pam_pertemuan10.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pam_pertemuan10.data.dao.DosenDao
import com.example.pam_pertemuan10.data.entity.Dosen

@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class DatabaseDosen : RoomDatabase(){

    abstract fun dosenDao(): DosenDao

    companion object {
        @Volatile //
        private var Instance: DatabaseDosen? = null

        // Membuat database
        fun getDatabase(context: Context): DatabaseDosen {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DatabaseDosen::class.java, // Class database
                    "DatabaseDosen" // Nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}



