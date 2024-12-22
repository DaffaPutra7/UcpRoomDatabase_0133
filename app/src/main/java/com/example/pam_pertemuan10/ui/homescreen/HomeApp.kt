package com.example.pam_pertemuan10.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pam_pertemuan10.R

@Composable
fun HomeApp(
    onHalamanHomeDosen: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF8C1515) // Harvard Crimson Color
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tulisan Harvard University
        Text(
            text = "Harvard University",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Logo Harvard
        Image(
            painter = painterResource(
                id = R.drawable.harvard // Ganti dengan ID logo Harvard Anda
            ),
            contentDescription = "Logo Harvard",
            modifier = Modifier
                .size(200.dp) // Ukuran logo lebih besar
                .padding(bottom = 24.dp)
        )

        // Tombol "Dosen"
        Button(
            onClick = { onHalamanHomeDosen() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White // Tombol putih
            )
        ) {
            Text(
                text = "Dosen",
                fontSize = 18.sp,
                color = Color.Black, // Teks hitam
                fontWeight = FontWeight.Bold
            )
        }

        // Spacer untuk jarak antar tombol
        Spacer(modifier = Modifier.height(16.dp))

        // Tombol "Matakuliah"
        Button(
            onClick = { onHalamanHomeMK() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White // Tombol putih
            )
        ) {
            Text(
                text = "Matakuliah",
                fontSize = 18.sp,
                color = Color.Black, // Teks hitam
                fontWeight = FontWeight.Bold
            )
        }
    }
}
