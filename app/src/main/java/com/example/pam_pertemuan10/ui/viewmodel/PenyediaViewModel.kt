package com.example.pam_pertemuan10.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pam_pertemuan10.KrsApp

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            InsertDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            InsertMKViewModel(
                krsApp().containerApp.repositoryMK,
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeMKViewModel(
                krsApp().containerApp.repositoryMK
            )
        }

        initializer {
            DetailMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMK
            )
        }

        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMK,
                krsApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)