package com.example.pam_pertemuan10.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_pertemuan10.data.entity.MataKuliah
import com.example.pam_pertemuan10.repository.RepositoryMK
import com.example.pam_pertemuan10.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK,
) : ViewModel() {
    private val _kode: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiDetailMK.Kode])

    val detailUiState: StateFlow<DetailUiState> = repositoryMK.getMataKuliah(_kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMK() {
        detailUiState.value.detailUiEvent.toMkEntity().let {
            viewModelScope.launch {
                repositoryMK.deleteMataKuliah(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MkEvent = MkEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MkEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MkEvent()
}

fun MataKuliah.toDetailUiEvent(): MkEvent {
    return MkEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}