package com.example.pam_pertemuan10.ui.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeApp(
    onHalamanHomeDosen: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {onHalamanHomeDosen()},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dosen")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {onHalamanHomeMK()},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Matakuliah")
        }
    }
}