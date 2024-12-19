package com.example.pam_pertemuan10.repository

import com.example.pam_pertemuan10.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {

    fun getAllDosen() : Flow<List<Dosen>>

    fun getDosen(nidn: String): Flow<Dosen>

    suspend fun insertDosen(dosen: Dosen)

}