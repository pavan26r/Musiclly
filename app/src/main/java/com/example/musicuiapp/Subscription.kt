package com.example.musicuiapp

import android.R.attr.bottom
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "subscription_screen") {
        composable("subscription_screen") {
            Subscription(navController)
        }
        composable("plans_screen") {
            Plans_screen(navController)
        }
        composable(
            route = "qr_screen/{planName}/{planPrice}",
            arguments = listOf(
                navArgument("planName") { type = NavType.StringType },
                navArgument("planPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("planName") ?: ""
            val price = backStackEntry.arguments?.getString("planPrice") ?: ""
            QRScreen(name, price)
        }
    }
}

@Composable
fun Subscription(navController: NavController) {
    Column(
        modifier = Modifier.height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Manage Subscription")

        Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
            Column(modifier = Modifier.padding(8.dp)) {

                // Removed extra nested Column — no need for wrapping one column inside another
                Text("Musical")

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("free trial")

                    TextButton(onClick = {
                        navController.navigate("plans_screen")
                    }) {
                        Row {
                            Text("See All Plans")
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "See All Plans"
                            )
                        }
                    }
                }

                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 8.dp))

                Row(Modifier.padding(vertical = 16.dp)) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Get a Plan"
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Added spacing between icon & text
                    Text(text = "Get a Plan")
                }
            }
        }
    }
}

@Composable
fun Plans_screen(navController: NavController) {
    val plans = listOf(
        "Individual Plan" to "99",
        "Roommate Plan" to "149",
        "Family Plan" to "199",
        "Married Couple Plan" to "129"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Plans",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        plans.forEach { (name, price) ->
            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = name, style = MaterialTheme.typography.h6)
                        Text(
                            text = "₹$price/month",
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                        Text(
                            text = "bhai lele plz \uD83D\uDE4F",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )

                    }

                    Button(
                        onClick = {
                            navController.navigate("qr_screen/$name/$price")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF1DB954),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Select")
                    }
                }
            }
        }
    }
}

@Composable
fun QRScreen(planName: String, planPrice: String) {
    var remainingTime by remember { mutableIntStateOf(30) } // 30 seconds countdown
    var qrVisible by remember { mutableStateOf(true) }   // Show/hide QR

    // Countdown logic
    LaunchedEffect(Unit) {
        while (remainingTime > 0) {
            delay(1000L) // wait 1 second
            remainingTime--
        }
        qrVisible = false // hide QR after time runs out
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$planName - ₹$planPrice/month",
            style = MaterialTheme.typography.h6,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        IndianFlag(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        Text(text =" Bharat Mata Ki Jai")
        Text(
            text = " This Page Gonna Expires in $remainingTime seconds",
            color = Color.Green
        )

     if(qrVisible){
        Image(
            painter = painterResource(id = R.drawable.qrimage),
            contentDescription = "QR Code",
            modifier = Modifier
                .fillMaxSize() // Changed from fillMaxSize to size to avoid overly large image
                .padding(8.dp)
        )

        Text(
            text = "Scan this QR to pay",
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )

     }
        else{
      Text(
          text = "Session Expired",
          color = Color.Red,
          style = MaterialTheme.typography.h6
      )
    }
    }
}
@Composable
fun IndianFlag(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val flagHeight = size.height / 3

        // Saffron
        drawRect(
            color = Color(0xFFFF9933),
            topLeft = Offset(0f, 0f),
            size = Size(size.width, flagHeight)
        )

        // White
        drawRect(
            color = Color.White,
            topLeft = Offset(0f, flagHeight),
            size = Size(size.width, flagHeight)
        )

        // Green
        drawRect(
            color = Color(0xFF138808),
            topLeft = Offset(0f, flagHeight * 2),
            size = Size(size.width, flagHeight)
        )

        // Ashoka Chakra
        val chakraRadius = flagHeight / 2.5f
        val center = Offset(size.width / 2, flagHeight * 1.5f)

        // Outer circle
        drawCircle(
            color = Color(0xFF000080),
            radius = chakraRadius,
            center = center,
            style = Stroke(width = 4f)
        )

        // 24 spokes
        for (i in 0 until 24) {
            val angle = Math.toRadians((i * 15).toDouble())
            val endX = center.x + chakraRadius * cos(angle).toFloat()
            val endY = center.y + chakraRadius * sin(angle).toFloat()
            drawLine(
                color = Color(0xFF000080),
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 2f
            )
        }
    }
}

