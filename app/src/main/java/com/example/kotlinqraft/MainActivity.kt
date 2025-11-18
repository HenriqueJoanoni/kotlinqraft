package com.example.kotlinqraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.example.kotlinqraft.ui.theme.KotlinQraftTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinQraftTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountriesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ViewModel
class CountriesViewModel : ViewModel() {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://countries.trevorblades.com/graphql")
        .build()

    var countriesState by mutableStateOf<List<GetCountriesQuery.Country>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun fetchCountries() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = apolloClient.query(GetCountriesQuery()).execute()
                countriesState = response.data?.countries?.filterNotNull() ?: emptyList()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}

// UI
@Composable
fun CountriesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = remember { CountriesViewModel() }

    LaunchedEffect(Unit) {
        viewModel.fetchCountries()
    }

    Column(modifier = modifier.fillMaxSize()) {
        when {
            viewModel.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            viewModel.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: ${viewModel.error}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fetchCountries() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.countriesState) { country ->
                        CountryCard(country)
                    }
                }
            }
        }
    }
}

@Composable
fun CountryCard(country: GetCountriesQuery.Country) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji
            Text(
                text = country.emoji,
                style = MaterialTheme.typography.displaySmall
            )

            // Country details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Capital: ${country.capital ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Currency: ${country.currency ?: "N/A"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Country code
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = country.code,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}