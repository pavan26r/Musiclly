package com.example.musicuiapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun BrowseItemWithGridBG(
    categoryName: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = categoryName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )

            // Overlay for text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f))
            )

            Text(
                text = categoryName,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun BrowseScreen(onCategoryClick: (String) -> Unit) {
    val categories = listOf(
        "Hits" to R.drawable.hits,
        "Happy" to R.drawable.happy,
        "Workout" to R.drawable.workout,
        "Yoga" to R.drawable.yoga,
        "Bhajan" to R.drawable.bhakti,
        "Running" to R.drawable.workout

    )

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categories) { (name, image) ->
            BrowseItemWithGridBG(categoryName = name, imageRes = image) {
                onCategoryClick(name)
            }
        }
    }
}

@Composable
fun SongListScreen() {
    val context = LocalContext.current
    val storage = FirebaseStorage.getInstance()
    val songs = remember { mutableStateListOf<Pair<String, String>>() }
    val player = remember { ExoPlayer.Builder(context).build() }

    val currentSongIndex = remember { mutableIntStateOf(-1)}
        val isPlaying = remember {mutableStateOf(false)}
    // Release player on exit
    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    // Fetch songs
    LaunchedEffect(Unit) {
        val result = storage.reference.child("bhajan/").listAll().await()
        result.items.forEach { fileRef ->
            val uri = fileRef.downloadUrl.await()
            println("Song: ${fileRef.name}, URL: $uri") // Debug
            songs.add(fileRef.name to uri.toString())
        }
    }
        LazyColumn {
            itemsIndexed(songs) { index,(name,url) ->
                SongItem(
                    songName = name,
                    isPlaying = currentSongIndex.intValue == index && isPlaying.value,
                    onPlay = {
                        val mediaItem = MediaItem.fromUri(url)
                        player.setMediaItem(mediaItem)
                        player.prepare()
                        player.play()
                        currentSongIndex.intValue = index
                        isPlaying.value = true
                    },
                    onPause = {
                        player.pause()
                        isPlaying.value = false
                    }
                )
            }
        }
    }


// Data Model


@Composable
fun SongItem(songName: String,isPlaying:Boolean, onPlay: () -> Unit,onPause:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { if(isPlaying) onPause else onPlay() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp).clickable{ if (isPlaying) onPause() else onPlay()},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = Color(0xFF6200EE),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = songName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}
