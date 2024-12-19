package com.example.pam_pertemuan10.repository

import com.example.pam_pertemuan10.data.dao.DosenDao
import com.example.pam_pertemuan10.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen(
    private val dosenDao: DosenDao) :
    RepositoryDosen
{

    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }

    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

}