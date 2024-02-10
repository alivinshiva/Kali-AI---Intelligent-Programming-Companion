package com.example.kaliai

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
//import com.example.kaliai.BuildConfig
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val interfaceState: MutableStateFlow<HomeInterfaceState> =
        MutableStateFlow(HomeInterfaceState.Initial)
    val _interfaceState = interfaceState.asStateFlow()
    private var generativeModel: GenerativeModel

    init {
        val config = generationConfig {
            temperature = 0.70f // values lies between 0-1
        }
        generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey,
            generationConfig = config
        )
    }

    fun dataProviding(userInput: String, selectedImage: List<Bitmap>) {
        interfaceState.value = HomeInterfaceState.Loading
        val prompt =

            "Welcome to Kali AI, your intelligent programming and development companion! 🚀" +
                    " Unleash the power of knowledge with Kali AI, your go-to source for " +
                    "information on programming languages, coding practices, development theories," +
                    " algorithms, and code analysis. Whether you're a beginner seeking guidance or an " +
                    "experienced developer looking to stay ahead, Kali AI is here to assist. Ask questions," +
                    " explore concepts, and analyze code snippets effortlessly. Empower your coding journey with " +
                    "Kali AI, where information meets innovation. How can we help you today?," +
                    " you are programmed by an engineering student name Alivin Shiva  : $userInput "
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    for (bitmap in selectedImage) {
                        image(bitmap)
                    }
                    text(prompt)
                }
                var output = ""
                generativeModel.generateContentStream(content).collect {
                    output += it.text
                    interfaceState.value = HomeInterfaceState.Success("Your query->\n  $userInput.\n\nAnswer...\n $output")
                }
            } catch (e: Exception) {
                interfaceState.value =
                    HomeInterfaceState.Error(e.localizedMessage ?: "Error Try Again...")
            }
        }

    }
}


sealed interface HomeInterfaceState {
    object Initial : HomeInterfaceState
    object Loading : HomeInterfaceState
    data class Success(val outputText: String) : HomeInterfaceState
    data class Error(val error: String) : HomeInterfaceState
}