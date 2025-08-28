package com.example.musicuiapp

import androidx.annotation.DrawableRes

data class Lib(@DrawableRes val icon:Int, val name:String)
val libraries = listOf<Lib>(
    Lib(R.drawable.baseline_playlist_add_check_24,"Playlist"),
    Lib(R.drawable.outline_artist_24,"Artist"),
    Lib(R.drawable.outline_album_24,"Album"),
    Lib(R.drawable.outline_tv_gen_24,"Genre"),
    Lib(R.drawable.baseline_music_note_24,"Songs"),
    )
