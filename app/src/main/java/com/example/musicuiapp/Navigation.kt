import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicuiapp.AccountView
import com.example.musicuiapp.BrowseScreen
import com.example.musicuiapp.Home
import com.example.musicuiapp.Library
import com.example.musicuiapp.MainViewModel
import com.example.musicuiapp.MyApp
import com.example.musicuiapp.Plans_screen
import com.example.musicuiapp.Screen
import com.example.musicuiapp.SongListScreen
import com.example.musicuiapp.Subscription
import com.example.musicuiapp.face.EmotionScreen

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Add.route,
        modifier = Modifier.padding(pd)
    ) {
        composable(Screen.BottomScreen.Home.route) {
            Home(navController)
        }
        composable(Screen.BottomScreen.Browse.route) {
            BrowseScreen { category ->
                navController.navigate("songs/$category")
            }
        }
        composable(Screen.BottomScreen.Library.route) {
            Library(navController)
        }
        composable(Screen.DrawerScreen.Add.route) {
            AccountView(navController)
        }
        composable(Screen.DrawerScreen.Subscription.route) {
            MyApp()
        }
        composable("plans_Screen"){
            Plans_screen(navController)
        }
        composable(Screen.BottomScreen.Browse.route){
            BrowseScreen { category ->
                navController.navigate("songs/$category")
            }
        }
        composable("songs/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            SongListScreen()
        }
        composable("accountView") { AccountView(navController) }
        composable("emotionScreen") { EmotionScreen() }


    }}
