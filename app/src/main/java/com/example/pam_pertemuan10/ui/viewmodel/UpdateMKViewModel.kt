package com.example.pam_pertemuan10.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_pertemuan10.data.entity.Dosen
import com.example.pam_pertemuan10.data.entity.MataKuliah
import com.example.pam_pertemuan10.repository.RepositoryDosen
import com.example.pam_pertemuan10.repository.RepositoryMK
import com.example.pam_pertemuan10.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdateMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val dosenUpdateState: StateFlow<List<Dosen>> = repositoryDosen.getAllDosen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var dosenListUpdate by mutableStateOf<List<String>>(emptyList())
        private set

    fun fetchDosenUpdateList() {
        viewModelScope.launch {
            try {
                repositoryDosen.getAllDosen()
                    .collect { dosenList ->
                        dosenListUpdate = dosenList.map { it.nama }
                    }
            } catch (e: Exception) {
                dosenListUpdate = emptyList()
            }
        }
    }

    var updateUIState by mutableStateOf(MkUiState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiUpdateMK.Kode])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMK.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMK()
        }
    }

    fun updateMkState (mkEvent: MkEvent) {
        updateUIState = updateUIState.copy(
            mkEvent = mkEvent,
        )
    }

    fun validateMKFields(): Boolean {
        val event = updateUIState.mkEvent
        val errorState = MkErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode Mata Kuliah tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Mata Kuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Jumlah SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Mata Kuliah tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateDataMk() {
        val currentEvent = updateUIState.mkEvent

        if (validateMKFields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.updateMataKuliah(currentEvent.toMkEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Mata Kuliah berhasil diupdate",
                        mkEvent = MkEvent(),
                        isEntryValid = MkErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Mata Kuliah gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data Mata Kuliah gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }

}

fun MataKuliah.toUIStateMK(): MkUiState = MkUiState(
    mkEvent = this.toDetailUiEvent(),
)