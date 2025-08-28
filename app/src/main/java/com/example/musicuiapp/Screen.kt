package com.example.musicuiapp

import androidx.annotation.DrawableRes // This is the main import needed
// No other explicit imports are strictly required for the classes and objects defined below
// com.example.musicuiapp.R is automatically available within this package.

// it is a sealed class that hold all the screen ->
sealed class Screen(var title: String, val route: String) {
    // sealed class for drawer ->

    sealed class BottomScreen(
        val btitle: String,
        val broute: String,
        @DrawableRes val icon: Int
    ) : Screen(btitle, broute) { // This BottomScreen now directly inherits from Screen
        object Home : BottomScreen(
            "Home",
            "home",
            R.drawable.ic_music
        )

        object Library : BottomScreen(
            "Library",
            "library",
            R.drawable.baseline_video_library_24
        )

        object Browse : BottomScreen(
            "Browse",
            "browse",
            R.drawable.baseline_apps_24
        )
    }

    sealed class DrawerScreen(
        val dtitle: String,
        val droute: String,
        @DrawableRes val icon: Int
    ) : Screen(dtitle, droute) {
        object Account : DrawerScreen(
            "Account",
            "account",
            R.drawable.baseline_account_box_24
        )

        object Subscription : DrawerScreen(
            "Subscription",
            "Subscribe", // Note: The route is "Subscribe", title "Subscription"
            R.drawable.outline_library_add_24
        )

        object Add : DrawerScreen(
            "Add Account",
            "add_account",
            R.drawable.baseline_person_add_24
        )
    }
}

val screeninbottom = listOf(
             Screen.BottomScreen.Home ,
             Screen.BottomScreen.Browse,
            Screen.BottomScreen.Library
)
val screenindrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.Add
)

// Example of a list for bottom navigation items, if you intended to have one:
val bottomNavigationItems = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Library,
    Screen.BottomScreen.Browse
)

