package com.crexative.imagepickercompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.crexative.imagepickercompose.ui.theme.ImagePickerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ImagePickerComposeTheme {
                var selectedImageUri by remember {
                    mutableStateOf<Uri?>(null)
                }

                var selectedImagesUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }

                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        selectedImageUri = uri
                    }
                )

                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris ->
                        selectedImagesUris = uris
                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick one photo")
                            }
                            Button(onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick multiple photo")
                            }
                        }
                    }

                    item {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    items(selectedImagesUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}