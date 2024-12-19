package com.example.pam_pertemuan10.repository

import com.example.pam_pertemuan10.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMK {

    fun getAllMataKuliah() : Flow<List<MataKuliah>>

    fun getMataKuliah(kode: String): Flow<MataKuliah>

    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

}