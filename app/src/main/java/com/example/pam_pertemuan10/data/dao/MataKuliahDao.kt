package com.example.pam_pertemuan10.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pam_pertemuan10.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {

    @Query(" SELECT * FROM matakuliah ORDER BY nama ASC")
    fun getAllMataKuliah() : Flow<List<MataKuliah>>

    @Insert
    suspend fun insertMataKuliah(
        mataKuliah: MataKuliah
    )

    @Delete
    suspend fun deleteMataKuliah(
        mataKuliah: MataKuliah
    )

    @Update
    suspend fun updateMataKuliah(
        mataKuliah: MataKuliah
    )
}