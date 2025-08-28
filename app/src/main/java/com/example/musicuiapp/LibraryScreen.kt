package com.example.musicuiapp

import android.R.attr.contentDescription
import android.R.attr.onClick
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Library(navController: NavController){
    LazyColumn(){
        items(libraries){
            lib ->
            Libitem(lib = lib ,onClick ={
                if(lib.name == "Songs"){
                    // nav controller please navigatr me to this ->
                    navController.navigate("songs/${lib.name}")
            }})


        }
    }
}
@Composable
fun Libitem(lib: Lib,onClick: () -> Unit){
    Column(
        modifier = Modifier.clickable{onClick()}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    painter = painterResource(id = lib.icon),
                    modifier = Modifier.padding(horizontal = 8.dp), contentDescription = lib.name,)
                    Text(text = lib.name)

            }
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Arrowright")
        }
        Divider(color = Color.LightGray)
    }
}