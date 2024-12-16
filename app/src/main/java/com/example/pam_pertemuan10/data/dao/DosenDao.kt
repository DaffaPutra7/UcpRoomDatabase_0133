package com.example.pam_pertemuan10.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pam_pertemuan10.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {

    @Query(" SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen() : Flow<List<Dosen>>

    @Insert
    suspend fun insertDosen(
        dosen: Dosen
    )
}