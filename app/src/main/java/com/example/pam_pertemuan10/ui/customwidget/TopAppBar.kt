package com.example.pam_pertemuan10.ui.customwidget

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
    modifier: Modifier = Modifier
) {
    SmallTopAppBar(
        title = {
            Text(
                text = judul,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333) // Dark gray text
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color(0xFF8C1515) // Crimson icon
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFF9F9F9), // Light background
            navigationIconContentColor = Color(0xFF8C1515), // Crimson
            titleContentColor = Color(0xFF333333) // Dark gray
        ),
        modifier = modifier
    )
}
