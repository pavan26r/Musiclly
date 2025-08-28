package com.example.musicuiapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AccountView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Account",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            text = "Pavan Bhai",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "@Pavan bhai",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                // My Music
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("songs/") }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_music),
                        contentDescription = "My Music",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "My Music",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                // My Music
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("emotionScreen") }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_upload_file_24),
                        contentDescription = "Find Emotion",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 12.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Find Your Emotion",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
