package com.example.musicuiapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // ✅ Added for click
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController // ✅ Added for navigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navController: NavController) { // ✅ NavController parameter added
    val categories = listOf("Hits", "Happy", "Workout", "Running", "Bhakti", "Yoga")

    val grouped: Map<Char, List<String>> =
        listOf("New Release", "Favourite", "Top Rated").groupBy { it[0] }

    // ✅ Map category names to images
    val categoryImages = mapOf(
        "Hits" to R.drawable.hits,
        "Happy" to R.drawable.happy,
        "Workout" to R.drawable.workout,
        "Running" to R.drawable.running,
        "Bhakti" to R.drawable.bhakti,
        "Yoga" to R.drawable.yoga
    )

    LazyColumn {
        grouped.entries.forEach { (groupChar, groupItems) ->
            stickyHeader(key = groupChar) {
                Text(
                    text = groupItems[0],
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    items(categories) { cat ->
                        val imageRes = categoryImages[cat] ?: R.drawable.baseline_music_note_24

                        // ✅ Pass click action to BrowseItem
                        BrowseItem(
                            cat = cat,
                            drawable = imageRes,
                            onClick = {
                                navController.navigate("songs/$cat") // Navigate to SongListScreen
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BrowseItem(cat: String, drawable: Int, onClick: () -> Unit) { // ✅ Added onClick param
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(150.dp)
            .height(180.dp)
            .clickable { onClick() }, // ✅ Card is now clickable
        border = BorderStroke(3.dp, color = Color.DarkGray),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = cat,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cat,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(bottom = 8.dp)
            )
        }
    }
}
