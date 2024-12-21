package com.example.pam_pertemuan10.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_pertemuan10.ui.homescreen.HomeApp
import com.example.pam_pertemuan10.ui.view.dosen.DestinasiInsertDosen
import com.example.pam_pertemuan10.ui.view.dosen.HomeDosenView
import com.example.pam_pertemuan10.ui.view.dosen.InsertDosenView
import com.example.pam_pertemuan10.ui.view.matakuliah.DestinasiInsertMK
import com.example.pam_pertemuan10.ui.view.matakuliah.DetailMKView
import com.example.pam_pertemuan10.ui.view.matakuliah.HomeMKView
import com.example.pam_pertemuan10.ui.view.matakuliah.InsertMkView
import com.example.pam_pertemuan10.ui.view.matakuliah.UpdateMKView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = AlamatNavigasi.DestinasiHomeApp.route
    ) {

        composable(
            route = AlamatNavigasi.DestinasiHomeApp.route
        ){
            HomeApp(
                onHalamanHomeDosen = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeDosen.route)
                },
                onHalamanHomeMK = {
                    navController.navigate(AlamatNavigasi.DestinasiHomeMK.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = AlamatNavigasi.DestinasiHomeDosen.route
        ){
            HomeDosenView(
                onAddDosen = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                onBack = {
                    navController.popBackStack()
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

        composable(
            route = AlamatNavigasi.DestinasiHomeMK.route
        ){
            HomeMKView(
                onDetailClick = { kode ->
                    navController.navigate("${AlamatNavigasi.DestinasiDetailMK.route}/$kode")
                    println(
                        "PengelolaHalaman: kode = $kode"
                    )
                },
                onBack = {
                    navController.popBackStack()
                },
                onAddMk = {
                    navController.navigate(DestinasiInsertMK.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertMK.route
        ){
            InsertMkView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            AlamatNavigasi.DestinasiDetailMK.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiDetailMK.Kode) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(AlamatNavigasi.DestinasiDetailMK.Kode)

            kode?.let { kode ->
                DetailMKView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${AlamatNavigasi.DestinasiUpdateMK.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            AlamatNavigasi.DestinasiUpdateMK.routeWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiUpdateMK.Kode) {
                    type = NavType.StringType
                }
            )
        ){

            UpdateMKView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}