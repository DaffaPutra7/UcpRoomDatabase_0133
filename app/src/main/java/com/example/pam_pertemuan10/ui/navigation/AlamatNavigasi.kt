package com.example.pam_pertemuan10.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinasiHomeDosen: AlamatNavigasi {
        override val route = "home"
    }

}