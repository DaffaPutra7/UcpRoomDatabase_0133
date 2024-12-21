package com.example.pam_pertemuan10.ui.navigation

interface AlamatNavigasi {
    val route: String

    object DestinasiHomeApp: AlamatNavigasi{
        override val route = "homeapp"
    }

    object DestinasiHomeDosen: AlamatNavigasi {
        override val route = "home"
    }

    object DestinasiHomeMK: AlamatNavigasi {
        override val route = "homemk"
    }

    object DestinasiDetailMK: AlamatNavigasi {
        override val route = "detailmk"
        const val Kode = "kode"
        val routeWithArg = "$route/{$Kode}"
    }

    object DestinasiUpdateMK: AlamatNavigasi {
        override val route = "updatemk"
        const val Kode = "kode"
        val routeWithArg = "$route/{$Kode}"
    }

}