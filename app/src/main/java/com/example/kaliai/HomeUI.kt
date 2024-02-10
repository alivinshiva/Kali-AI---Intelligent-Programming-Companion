package com.example.kaliai

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch

//@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Content(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val appInterfaceState = viewModel._interfaceState.collectAsState()
    LocalClipboardManager.current


    val coroutineScope = rememberCoroutineScope()

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
    val imageLoader = ImageLoader.Builder(LocalContext.current).build()

    HomeUI(interfaceState = appInterfaceState.value) { inputText, selectedItems ->

        coroutineScope.launch {
            val bitmaps = selectedItems.mapNotNull {
                val imageRequest = imageRequestBuilder
                    .data(it)
                    .size(size = 768)
                    .build()
                val imageResult = imageLoader.execute(imageRequest)
                if (imageResult is SuccessResult) {
                    return@mapNotNull (imageResult.drawable as BitmapDrawable).bitmap
                } else {
                    return@mapNotNull null
                }
            }
            viewModel.dataProviding(userInput = inputText, selectedImage = bitmaps)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable

fun HomeUI(
    interfaceState: HomeInterfaceState = HomeInterfaceState.Loading,
    onSendClicked: (String, List<Uri>) -> Unit
) {

    var userQuest by rememberSaveable {
        mutableStateOf("")
    }

    // 31 min 45sec completed 
    val imageUris = rememberSaveable(saver = UriCustomSaver()) {
        mutableStateListOf()
    }
//    val pickMediaLauncher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
//            val imageUri = null
//            imageUri?.let {
//                imageUris.add(it)
//            }
//        }


    Scaffold(
        topBar = {
//            Row( verticalAlignment = Alignment.CenterVertically) {
//
//                TopAppBar(
//                    title = {
//                        Text(
//                            text = "Kali AI",
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth(), // Ensure TopAppBar takes full width
//                    colors = TopAppBarDefaults.mediumTopAppBarColors(
//                        containerColor = Color.Transparent,
//                        titleContentColor = MaterialTheme.colorScheme.onPrimary
//
//                    )
//
//
//                )
////
////                     implementing the logo inside top bar
////                Column {
////                    Image(
////                        painter = painterResource(id =  R.drawable.solarized_dragon_kali_linux_hd_r2jtcz2ut8fs8eim_modified),
//////                        painter = painterResource(id = R.drawable.solarized_dragon_kali_linux_hd_r2jtcz2ut8fs8eim_modified), // Replace with your local image resource
////                        contentDescription = "app logo", // Content description for accessibility
////                        modifier = Modifier
////                            .padding(5.dp)
////                            .size(30.dp)
////                            .clip(CircleShape) // Clip to make it a circular shape
//////                        .offset(16.dp,5.dp)
////                    )
////                }
//
//
//            }
            // top bar here///
//            Text(
//                text = " Kali AI",
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//                color = MaterialTheme.colorScheme.onBackground,
//                fontSize = 5.em
//            )
            Card(
                modifier = Modifier
                    .padding(start = 140.dp, top = 10.dp, end = 140.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.solarized_dragon_kali_linux_hd_r2jtcz2ut8fs8eim_modified), // Replace with your local image resource
                        contentDescription = null, // Content description for accessibility
                        modifier = Modifier
                            .padding(5.dp)
                            .size(20.dp)
                            .clip(CircleShape) // Clip to make it a circular shape

                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = " Kali AI",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 5.em
                    )
                }
            }
            // copy text button
//            when (interfaceState) {
//                is HomeInterfaceState.Success -> {


//                    IconButton(
//                        onClick = {
//                            val clipboardManager
//                                if (interfaceState.outputText.isNotBlank()) {
//
//                                val clipData: ClipData = ClipData.newPlainText("text", interfaceState.outputText)
//                                clipboardManager.setPrimaryClip(clipData)
//                                // You can add a toast or a Snackbar to notify the user that the text has been copied
//                            }
//                        },
//                        modifier = Modifier.padding(4.dp)
//                    ) {
//                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
//                    }
//                }

//                else -> {}
//            }


        },


        bottomBar = {
            val controller =
                LocalSoftwareKeyboardController.current
            Column {
                Row(modifier = Modifier.padding(16.dp)) {

                    // Adding image icon
//                    IconButton(onClick = {
//                        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//
//                    }, modifier = Modifier.padding(4.dp)) {
//                        Icon(
//                            imageVector = Icons.Default.AddCircle,
//                            contentDescription = "Add Image"
//                        )
//
//                    }

                    // text Input box

                    TextField(
                        value = userQuest,
                        onValueChange = {
                            userQuest = it
                        },

//                        label = { Text(text = "User Input") },
                        placeholder = { Text(text = "Ask your queries...") },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .background(Color.Gray.copy(alpha = 0.1f), shape = CircleShape),
                        shape = CircleShape,
                        // this option will replace enter button with search icon in keyboard.
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search,
                            capitalization = KeyboardCapitalization.Sentences
                        ),// Capitalizing first word.
                        keyboardActions = KeyboardActions(onSearch = {
                            if (userQuest.isNotBlank()) {
                                onSendClicked(userQuest, imageUris)
                            }; userQuest = "" // for clearing textbox
                            controller?.hide()  // for hiding keyboard

                        }),

                        // this will hide underline from the textField.
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                    )

                    // Send button
                    IconButton(
                        onClick = {
                            if (userQuest.isNotBlank()) {
                                onSendClicked(userQuest, imageUris)
                            }
                            userQuest = ""  // for clearing text field
                            controller?.hide()  // for hiding keyboard
                        },
                        modifier = Modifier.padding(4.dp),

                        ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send")


                    }

                }
//                AnimatedVisibility(visible = imageUris.size > 0) {
//                    Card (modifier = Modifier.padding(8.dp)){
//
//                        LazyRow(modifier = Modifier.padding(8.dp)) {
//                            items(imageUris) { imageUri ->
//                                Column(
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    AsyncImage(
//                                        model = imageUri,
//                                        contentDescription = "",
//                                        modifier = Modifier
//                                            .padding(4.dp)
//                                            .requiredSize(50.dp)
//                                    )
//                                    TextButton(onClick = { imageUris.remove(imageUri) }) {
//                                        Text(text = "Remove")
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//
//
//                }
            }

        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (interfaceState) {
                is HomeInterfaceState.Initial -> {
//                     home page content here.
//                    Card(
//                        modifier = Modifier
//                            .padding()
//                            .fillMaxWidth()
//                        .size(width = 300.dp, height = 200.dp),
//                        shape = MaterialTheme.shapes.large
//                    ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.solarized_dragon_kali_linux_hd_r2jtcz2ut8fs8eim_modified), // Replace with your local image resource
                            contentDescription = null, // Content description for accessibility
                            modifier = Modifier
                                .padding(top = 350.dp)
                                .size(50.dp)

                                .clip(CircleShape) // Clip to make it a circular shape

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    Column (horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize())
                    {

                        Text(
                            text = "How can I help you today?",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 5.em
//                                    modifier = Modifier.paddingFromBaseline(top = 20.dp, bottom = 8.dp).padding(start = 67.dp, end = 70.dp),

                        )


                    }


                }

                is HomeInterfaceState.Loading -> {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                    Text(
                        text = "Kali Ai is thinking...",
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                }

                is HomeInterfaceState.Success -> {
                    Card(
                        modifier = Modifier
                            .padding()
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large
                    ) {


                        // User Icon at the top left corner
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.solarized_dragon_kali_linux_hd_r2jtcz2ut8fs8eim_modified), // Replace with your local image resource
                                contentDescription = null, // Content description for accessibility
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(30.dp)
                                    .clip(CircleShape) // Clip to make it a circular shape
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                        // making the text to be copy.
                        SelectionContainer {
                            Text(
                                text = interfaceState.outputText,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                    }
                }

                is HomeInterfaceState.Error -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.errorContainer)

                    ) {
                        Text(text = interfaceState.error)
                    }
                }
            }

        }

    }


}