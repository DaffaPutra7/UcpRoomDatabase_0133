package com.example.pam_pertemuan10.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_pertemuan10.ui.customwidget.TopAppBar
import com.example.pam_pertemuan10.ui.navigation.AlamatNavigasi
import com.example.pam_pertemuan10.ui.viewmodel.InsertMKViewModel
import com.example.pam_pertemuan10.ui.viewmodel.MkErrorState
import com.example.pam_pertemuan10.ui.viewmodel.MkEvent
import com.example.pam_pertemuan10.ui.viewmodel.MkUiState
import com.example.pam_pertemuan10.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertMK: AlamatNavigasi {
    override val route: String = "insertmk"
}

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMKViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.mkState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )
            InsertBodyMk(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateMkState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDataMk()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    onValueChange: (MkEvent) -> Unit,
    uiState: MkUiState,
    onClick:() -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            mkEvent = uiState.mkEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMataKuliah(
    mkEvent: MkEvent = MkEvent(),
    onValueChange:(MkEvent) -> Unit = {},
    errorState: MkErrorState = MkErrorState(),
    modifier: Modifier = Modifier
){
    val semester = listOf("Ganjil", "Genap")
    val jenis = listOf("Pemrograman", "Database", "Jaringan", "Desain")

    Column(modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mkEvent.nama,
            onValueChange = {
                onValueChange(mkEvent.copy(nama = it))
            },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
        )
        Text(
            text = errorState.nama ?:"",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mkEvent.kode,
            onValueChange = {
                onValueChange(mkEvent.copy(kode = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.kode?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester")
        Row (modifier = Modifier.fillMaxWidth())
        {
            semester.forEach{sem ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mkEvent.semester == sem,
                        onClick = {
                            onValueChange(mkEvent.copy(semester = sem))
                        },
                    )
                    Text(text = sem,)
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mkEvent.sks,
            onValueChange = {
                onValueChange(mkEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Jumlah SKS Mata Kuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?:"",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Mata Kuliah")
        Row (modifier = Modifier.fillMaxWidth())
        {
            jenis.forEach{jenis ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mkEvent.jenis == jenis,
                        onClick = {
                            onValueChange(mkEvent.copy(jenis = jenis))
                        },
                    )
                    Text(text = jenis,)
                }
            }
        }
    }
}