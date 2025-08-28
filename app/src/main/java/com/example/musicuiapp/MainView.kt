package com.example.musicuiapp

import AccountDialog
import Navigation
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicuiapp.auth.authviewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Screen(
    // i have to make one suspend state for opening the drawer ->
) {
    val viewModel: MainViewModel = viewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    // allow us to find out on which view we current are
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val dialogOpen = remember {
        mutableStateOf(false)
    }
    val currentScreen = remember {
        viewModel.currentScreen.value
    }

     val isSheeetFullScreen by remember{
         mutableStateOf(false)
     }
    val modalshettstate = rememberModalBottomSheetState(
     initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded}
    )
    val roundedCornerRadius = if(isSheeetFullScreen) 0.dp else 16.dp

    val isSheetFullScreen by remember { mutableStateOf(false)}
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    // Assuming viewModel.currentScreen.value.title is the initial title
    val title = remember { mutableStateOf(currentScreen.title) } // Use 'by' for delegation
    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {
            val tint =
                if (currentRoute == Screen.BottomScreen.Home.broute) Color.White else Color.Black
            BottomNavigation(Modifier.wrapContentSize()) {
                screeninbottom.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            controller.navigate(item.route)
                            title.value = item.title
                        }, icon = {
                            androidx.wear.compose.material3.Icon(
                                tint = tint, contentDescription = item.btitle,
                                painter = painterResource(id = item.icon)
                            )
                        },
                        label = { Text(text = item.btitle, color = Color.Black) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }
    ModalBottomSheetLayout(
        sheetState = modalshettstate,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius,topEnd = roundedCornerRadius
            ),
        sheetContent = {
        MoreBottomSheet(modifier = Modifier)
    }) {
        Scaffold(
            bottomBar = bottomBar,
            topBar = {
                TopAppBar(
                    title = { Text(title.value) },
                    actions = {IconButton(
                        onClick = {
                            scope.launch {
                                if (modalshettstate.isVisible) {
                                    modalshettstate.hide()
                                } else {
                                    modalshettstate.show()
                                }
                            }
                        }
                    ){
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                    }},
                    navigationIcon = {
                        IconButton(onClick = {

                            scope.launch {
                                // this is a library to open the drawer state ->
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                        }
                    }

                )
            }, scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(Modifier.padding(16.dp)) {
                    items(screenindrawer) { item ->
                        DrawerItem(selected = currentRoute == item.droute, item = item) {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if (item.droute == "add_account") {
                                dialogOpen.value = true
                            } else {
                                controller.navigate(item.droute)
                                title.value = item.dtitle
                            }

                        }
                    }
                }
            }
        ) {
            Navigation(
                navController = controller,
                viewModel = viewModel,
                pd = it
            )
             val authViewModel: AuthViewModel = viewModel()
            AccountDialog(dialogOpen = dialogOpen,authViewModel = authViewModel )
        }
    }
}
@Composable
fun DrawerItem(
    // is drawer selected ->
    selected: Boolean,
    // performed a function of click ->
    item: Screen.DrawerScreen,
    onDraweritemClicked: () -> Unit
) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(background)
            .clickable {
                onDraweritemClicked()
            }) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dtitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dtitle,
            style = MaterialTheme.typography.h5
        )
    }

}

@Composable
fun MoreBottomSheet(modifier: Modifier) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                MaterialTheme.colors.background
            )
    ) {
        Column(
            modifier = Modifier.padding(end = 16.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.baseline_settings_24), contentDescription = "Setting"
                )
               Text(text = "setting",fontSize = 20.sp,color = Color.White)
            }
        }
    }
}

