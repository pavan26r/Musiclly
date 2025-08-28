import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material.primarySurface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.musicuiapp.auth.authviewmodel.AuthViewModel

@Composable
fun AccountDialog(
    dialogOpen: MutableState<Boolean>,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    if (dialogOpen.value) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = {
                dialogOpen.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    authViewModel.login(
                        email = email.value,
                        password = password.value,
                        onResult = { success ->
                            if (success) {
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT)
                                    .show()
                                email.value = ""              // <-- Clear Email
                                password.value = ""           // <-- Clear Password
                                dialogOpen.value = false
                            } else {
                                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    dialogOpen.value = false
                }) {
                    Text("Login")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    authViewModel.signup(
                        email = email.value,
                        password = password.value,
                        onResult = { success ->
                            if (success) {
                                Toast.makeText(context, "Signup Succesfully", Toast.LENGTH_SHORT).show()
                                email.value = ""              // <-- Clear Email
                                password.value = ""           // <-- Clear Password
                                dialogOpen.value = false
                            } else {
                                Toast.makeText(context, "Signup Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    dialogOpen.value = false
                }) {
                    Text("Sign Up")
                }
            },
            title = {
                Text(text = "Add Account")
            },
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        modifier = Modifier.padding(top = 16.dp),
                        label = { Text(text = "Email") }
                    )
                    TextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        modifier = Modifier.padding(top = 16.dp),
                        label = { Text(text = "Password") }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(androidx.compose.material.MaterialTheme.colors.primarySurface)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}
