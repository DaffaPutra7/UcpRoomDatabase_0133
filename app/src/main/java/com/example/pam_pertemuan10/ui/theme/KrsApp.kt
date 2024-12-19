package com.example.pam_pertemuan10.ui.theme

import android.app.Application
import com.example.pam_pertemuan10.dependenciesinjection.ContainerApp

class KrsApp : Application() {

    lateinit var containerApp: ContainerApp
    override fun onCreate() {

        super.onCreate()

        containerApp = ContainerApp(this)
    }
}