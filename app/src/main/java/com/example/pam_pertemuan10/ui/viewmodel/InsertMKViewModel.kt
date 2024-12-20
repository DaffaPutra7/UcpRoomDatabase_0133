package com.example.pam_pertemuan10.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_pertemuan10.data.entity.MataKuliah
import com.example.pam_pertemuan10.repository.RepositoryMK
import kotlinx.coroutines.launch

class InsertMKViewModel(private val repositoryMK: RepositoryMK) : ViewModel() {
    var mkState by mutableStateOf(MkUiState())

    fun updateMkState(mkEvent: MkEvent){
        mkState = mkState.copy(
            mkEvent = mkEvent
        )
    }

    private fun validateMkFields(): Boolean{
        val event = mkState.mkEvent
        val errorState = MkErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode Mata Kuliah tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Mata Kuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Jumlah SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Mata Kuliah tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        mkState = mkState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveDataMk(){
        val currentEvent = mkState.mkEvent
        if (validateMkFields()){
            viewModelScope.launch {
                try {
                    repositoryMK.insertMataKuliah(currentEvent.toMkEntity())
                    mkState = mkState.copy(
                        snackBarMessage = "Data Mata Kuliah Berhasil Disimpan",
                        mkEvent = MkEvent(),
                        isEntryValid = MkErrorState()
                    )
                } catch (e: Exception) {
                    mkState = mkState.copy(snackBarMessage = "Data Mata Kuliah Gagal Disimpan")
                }
            }
        } else{
            mkState = mkState.copy(snackBarMessage = "Input tidak valid. Periksan kembali data mata kuliah Anda.")
        }
    }
    fun  resetSnackBarMessage(){
        mkState = mkState.copy(snackBarMessage = null)
    }

}

data class MkUiState(
    val mkEvent: MkEvent = MkEvent(),
    val isEntryValid: MkErrorState = MkErrorState(),
    val snackBarMessage: String? = null
)

data class MkErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null &&
                semester == null && jenis == null && dosenPengampu == null
    }
}

fun MkEvent.toMkEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MkEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)