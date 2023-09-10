package viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Bird
import state.BirdsUiState


class BirdsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    init {
        updateBirds()
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    private fun updateBirds() {
        viewModelScope.launch {
            val birds = getBirds()
            _uiState.update {
                it.copy(birds = birds)
            }
        }
    }

    private suspend fun getBirds(): List<Bird> {
        return httpClient
            .get("https://sebi.io/demo-image-api/pictures.json")
            .body()
    }
}