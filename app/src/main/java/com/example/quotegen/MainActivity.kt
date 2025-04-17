package com.example.quotegen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quotegen.ui.theme.QuoteGenTheme
import com.example.quotegen.viewmodel.QuoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuoteGenTheme {
                QuoteApp()
            }
        }
    }
}

@Composable
fun QuoteApp(
    viewModel: QuoteViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = uiState.backgroundColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { navController.navigate("saved") }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Saved Quotes",
                                tint = uiState.textColor
                            )
                        }
                    }

                    // Quote Content
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = uiState.currentQuote?.quote ?: "",
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    color = uiState.textColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "- ${uiState.currentQuote?.author ?: ""}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = uiState.textColor
                                )
                            }
                        }
                    }

                    // Bottom Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { viewModel.fetchRandomQuote() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = uiState.textColor,
                                contentColor = uiState.backgroundColor
                            )
                        ) {
                            Text("Next Quote")
                        }
                        Button(
                            onClick = { viewModel.saveCurrentQuote() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = uiState.textColor,
                                contentColor = uiState.backgroundColor
                            )
                        ) {
                            Text("Save Quote")
                        }
                    }
                }
            }
        }

        composable("saved") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Quotes",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Saved Quotes List
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.savedQuotes) { quote ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    viewModel.showQuote(quote)
                                    navController.navigate("home")
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = quote.quote,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        Text(
                                            text = "- ${quote.author}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    IconButton(onClick = { viewModel.deleteQuote(quote) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete Quote"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}