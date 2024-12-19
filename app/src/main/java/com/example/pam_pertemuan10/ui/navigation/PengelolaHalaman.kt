package com.example.pam_pertemuan10.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pam_pertemuan10.ui.view.dosen.DestinasiInsertDosen
import com.example.pam_pertemuan10.ui.view.dosen.HomeDosenView
import com.example.pam_pertemuan10.ui.view.dosen.InsertDosenView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = AlamatNavigasi.DestinasiHomeDosen.route
    ) {
        composable(
            route = AlamatNavigasi.DestinasiHomeDosen.route
        ){
            HomeDosenView(
                onAddDosen = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertDosen.route
        ){
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}