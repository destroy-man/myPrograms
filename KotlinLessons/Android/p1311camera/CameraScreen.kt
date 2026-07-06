package ru.korobeynikov.p1311camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.viewfinder.compose.MutableCoordinateTransformer
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    //capture photo and video recording
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var camera by remember { mutableStateOf<Camera?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var activeRecording by remember { mutableStateOf<Recording?>(null) }

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                activeRecording = toggleRecording(context, videoCapture, activeRecording)
            }
        }
    )

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }

        imageCapture =
            ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()
        val recorder =
            Recorder.Builder().setQualitySelector(QualitySelector.from(Quality.FHD)).build()
        videoCapture = VideoCapture.withOutput(recorder)

        camera = provider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageCapture,
            videoCapture
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
        ) {
            IconButton(onClick = {
                capturePhoto(context, imageCapture)
            }) {
                Icon(Icons.Default.PhotoCamera, "Take Photo")
            }

            Spacer(modifier = Modifier.width(32.dp))

            IconButton(onClick = {
                launcher.launch(Manifest.permission.RECORD_AUDIO)
            }) {
                Icon(
                    imageVector =
                        if (activeRecording == null) Icons.Default.RadioButtonUnchecked
                        else Icons.Default.Stop,
                    contentDescription = "Record Video"
                )
            }
        }
    }
}

private fun capturePhoto(context: Context, imageCapture: ImageCapture?) {
    val capture = imageCapture ?: return

    val name = "IMG_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, name)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val outputOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ).build()

    capture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Toast.makeText(context, "Снимок сохранен", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(context, "Снимок не сохранен", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

private fun toggleRecording(
    context: Context,
    videoCapture: VideoCapture<Recorder>?,
    currentRecording: Recording?,
): Recording? {
    val capture = videoCapture ?: return null

    currentRecording?.let {
        currentRecording.stop()
        return null
    }

    val name = "VID_${System.currentTimeMillis()}.mp4"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, name)
    }

    val outputOptions = MediaStoreOutputOptions.Builder(
        context.contentResolver,
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    ).setContentValues(contentValues).build()

    val permissionStatus =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
    return if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
        null
    } else capture.output
        .prepareRecording(context, outputOptions)
        .withAudioEnabled()
        .start(
            ContextCompat.getMainExecutor(context)
        ) { event -> }
}

@Composable
fun InteractiveCameraScreen(
    modifier: Modifier = Modifier,
    onFocusTap: (success: Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var camera by remember { mutableStateOf<Camera?>(null) }

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }
        camera =
            provider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview)
    }

    val coordinateTransformer = remember { MutableCoordinateTransformer() }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            coordinateTransformer = coordinateTransformer,
            modifier = modifier
                .fillMaxSize()
                .pointerInput(camera) {
                    detectTapGestures { offset ->
                        val cam = camera ?: return@detectTapGestures

                        val surfacePoint = with(coordinateTransformer) {
                            offset.transform()
                        }

                        val meteringFactory = SurfaceOrientedMeteringPointFactory(
                            request.resolution.width.toFloat(),
                            request.resolution.height.toFloat()
                        )

                        val focusPoint = meteringFactory.createPoint(surfacePoint.x, surfacePoint.y)

                        val action = FocusMeteringAction.Builder(
                            focusPoint,
                            FocusMeteringAction.FLAG_AF or FocusMeteringAction.FLAG_AE
                        ).setAutoCancelDuration(3, TimeUnit.SECONDS).build()

                        cam.cameraControl.startFocusAndMetering(action).addListener(
                            { onFocusTap(true) },
                            ContextCompat.getMainExecutor(context)
                        )
                    }
                }
                .pointerInput(camera) {
                    detectTransformGestures { _, _, zoom, _ ->
                        val cam = camera ?: return@detectTransformGestures
                        val zoomState =
                            cam.cameraInfo.zoomState.value ?: return@detectTransformGestures
                        val newRatio = (zoomState.zoomRatio * zoom).coerceIn(
                            zoomState.minZoomRatio,
                            zoomState.maxZoomRatio
                        )
                        cam.cameraControl.setZoomRatio(newRatio)
                    }
                }
        )
    }
}

@Composable
fun CameraWithLensSwitchScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    var useFront by rememberSaveable { mutableStateOf(false) }
    val selector =
        if (useFront) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA

    LaunchedEffect(selector) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }
        provider.unbindAll()
        provider.bindToLifecycle(lifecycleOwner, selector, preview)
    }

    Box(Modifier.fillMaxSize()) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = modifier.fillMaxSize()
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                useFront = !useFront
            }
        ) {
            Icon(Icons.Rounded.Cameraswitch, contentDescription = "Switch camera")
        }
    }
}

@Composable
fun SimpleCameraScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val surfaceRequests = remember { MutableStateFlow<SurfaceRequest?>(null) }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.awaitInstance(context)

        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }

        provider.unbindAll()
        provider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview)
    }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier.fillMaxSize()
        )
    }
}