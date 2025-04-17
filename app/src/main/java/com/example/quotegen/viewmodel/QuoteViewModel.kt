package com.example.quotegen.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotegen.api.QuoteApiService
import com.example.quotegen.data.Quote
import com.example.quotegen.data.QuoteDatabase
import com.example.quotegen.data.toQuote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class QuoteUiState(
    val currentQuote: Quote? = null,
    val savedQuotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black
)

class QuoteViewModel(application: Application) : AndroidViewModel(application) {
    private val quoteApi = QuoteApiService.create()
    private val quoteDao = QuoteDatabase.getDatabase(application).quoteDao()
    
    private val _uiState = MutableStateFlow(QuoteUiState())
    val uiState: StateFlow<QuoteUiState> = _uiState.asStateFlow()

    init {
        fetchRandomQuote()
        viewModelScope.launch {
            quoteDao.getAllQuotes().collect { quotes ->
                _uiState.value = _uiState.value.copy(savedQuotes = quotes)
            }
        }
    }

    fun fetchRandomQuote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val response = quoteApi.getRandomQuote()
                if (response.isNotEmpty()) {
                    val quote = response[0].toQuote()
                    val (backgroundColor, textColor) = generateRandomColors()
                    _uiState.value = _uiState.value.copy(
                        currentQuote = quote,
                        isLoading = false,
                        error = null,
                        backgroundColor = backgroundColor,
                        textColor = textColor
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun saveCurrentQuote() {
        viewModelScope.launch {
            _uiState.value.currentQuote?.let { quote ->
                quoteDao.insertQuote(quote)
            }
        }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch {
            quoteDao.deleteQuote(quote)
        }
    }

    fun showQuote(quote: Quote) {
        val (backgroundColor, textColor) = generateRandomColors()
        _uiState.value = _uiState.value.copy(
            currentQuote = quote,
            backgroundColor = backgroundColor,
            textColor = textColor
        )
    }

    private fun generateRandomColors(): Pair<Color, Color> {
        val backgroundColor = Color(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat(),
            1f
        )
        
        // Calculate the luminance to determine text color
        val luminance = (0.299 * backgroundColor.red + 
                        0.587 * backgroundColor.green + 
                        0.114 * backgroundColor.blue)
        
        val textColor = if (luminance > 0.5) Color.Black else Color.White
        return backgroundColor to textColor
    }
} 