package com.example.pam_pertemuan10.repository

import com.example.pam_pertemuan10.data.dao.MataKuliahDao
import com.example.pam_pertemuan10.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMK(

    private val mataKuliahDao: MataKuliahDao) :
    RepositoryMK

{

    override fun getAllMataKuliah(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMataKuliah()
    }

    override fun getMataKuliah(kode: String): Flow<MataKuliah> {
        return mataKuliahDao.getMataKuliah(kode)
    }

    override suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }

    override suspend fun deleteMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    override suspend fun updateMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }

}