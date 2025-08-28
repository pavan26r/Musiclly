import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(onImageCaptured: (File) -> Unit) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    var capturedFile by remember { mutableStateOf<File?>(null) }
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }
    if (!permissionState.status.isGranted) {
        Text("Camera permission is required")
        return
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (capturedFile != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberAsyncImagePainter(capturedFile),
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Button(
                    onClick = { capturedFile = null }, // reset state
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Retake")
                }
            }
        } else {
            val lifecycleOwner = LocalLifecycleOwner.current
            val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
            val previewView = remember { PreviewView(context) }
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            LaunchedEffect(Unit) {
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val imageCapture = ImageCapture.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                delay(3000)
                val file = File(context.cacheDir, "captured.jpg")
                val output = ImageCapture.OutputFileOptions.Builder(file).build()
                imageCapture.takePicture(
                    output,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            capturedFile = file
                            onImageCaptured(file)
                        }
                        override fun onError(exc: ImageCaptureException) {
                            Log.e("CameraPreview", "Capture failed", exc)
                        }
                    }
                )
            }
        }
    }
}