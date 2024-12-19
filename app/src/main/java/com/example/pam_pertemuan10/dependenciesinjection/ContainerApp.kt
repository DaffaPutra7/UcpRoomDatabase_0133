package com.example.pam_pertemuan10.dependenciesinjection

import android.content.Context
import com.example.pam_pertemuan10.data.database.DatabaseDosen
import com.example.pam_pertemuan10.repository.LocalRepositoryDosen
import com.example.pam_pertemuan10.repository.LocalRepositoryMK
import com.example.pam_pertemuan10.repository.RepositoryDosen
import com.example.pam_pertemuan10.repository.RepositoryMK

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen

    val repositoryMK: RepositoryMK
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(DatabaseDosen.getDatabase(context).dosenDao())
    }

    override val repositoryMK: RepositoryMK by lazy {
        LocalRepositoryMK(DatabaseDosen.getDatabase(context).matakuliahDao())
    }

}