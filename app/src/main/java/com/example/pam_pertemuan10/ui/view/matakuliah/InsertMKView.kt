package com.example.pam_pertemuan10.ui.view.matakuliah

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_pertemuan10.data.entity.Dosen
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
) {
    val uiState = viewModel.mkState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dosenList by viewModel.dosenListState.collectAsState()

    // Panggil fetchDosenList saat pertama kali komponen di-load
    LaunchedEffect(Unit) {
        viewModel.fetchDosenList()
    }

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
    ) { padding ->
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
                onValueChange = { updateEvent ->
                    viewModel.updateMkState(updateEvent)
                },
                dosenList = dosenList,
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
    dosenList: List<Dosen>,
    onValueChange: (MkEvent) -> Unit,
    uiState: MkUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Scrollable
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        FormMataKuliah(
            mkEvent = uiState.mkEvent,
            onValueChange = onValueChange,
            dosenList = dosenList,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol Simpan
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8C1515), // Crimson color
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Simpan",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun FormMataKuliah(
    mkEvent: MkEvent = MkEvent(),
    dosenList: List<Dosen>,
    onValueChange: (MkEvent) -> Unit = {},
    errorState: MkErrorState = MkErrorState(),
    modifier: Modifier = Modifier
) {
    val semesterOptions = listOf("Ganjil", "Genap")
    val jenisOptions = listOf("Wajib", "Peminatan")
    var selectedDosen by remember { mutableStateOf(mkEvent.dosenPengampu) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Nama Mata Kuliah
        OutlinedTextField(
            value = mkEvent.nama,
            onValueChange = { onValueChange(mkEvent.copy(nama = it)) },
            label = { Text("Nama Mata Kuliah") },
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
            isError = errorState.nama != null,
            modifier = Modifier.fillMaxWidth()
        )
        errorState.nama?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        // Kode Mata Kuliah
        OutlinedTextField(
            value = mkEvent.kode,
            onValueChange = { onValueChange(mkEvent.copy(kode = it)) },
            label = { Text("Kode Mata Kuliah") },
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
            isError = errorState.kode != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        errorState.kode?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        // Semester
        Text("Semester", style = MaterialTheme.typography.bodyMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            semesterOptions.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = mkEvent.semester == option,
                        onClick = { onValueChange(mkEvent.copy(semester = option)) }
                    )
                    Text(text = option)
                }
            }
        }

        // SKS
        OutlinedTextField(
            value = mkEvent.sks,
            onValueChange = { onValueChange(mkEvent.copy(sks = it)) },
            label = { Text("SKS") },
            placeholder = { Text("Masukkan Jumlah SKS") },
            isError = errorState.sks != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        errorState.sks?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        // Jenis Mata Kuliah
        Text("Jenis Mata Kuliah", style = MaterialTheme.typography.bodyMedium)
        jenisOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = mkEvent.jenis == option,
                    onClick = { onValueChange(mkEvent.copy(jenis = option)) }
                )
                Text(text = option)
            }
        }

        // Dosen Pengampu
        Text("Dosen Pengampu", style = MaterialTheme.typography.bodyMedium)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = {},
                label = { Text("Pilih Dosen Pengampu") },
                placeholder = { Text("Klik untuk memilih dosen") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        text = { Text(dosen.nama) },
                        onClick = {
                            selectedDosen = dosen.nama
                            onValueChange(mkEvent.copy(dosenPengampu = dosen.nama))
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
