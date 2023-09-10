package state

import model.Bird

data class BirdsUiState(
    val birds: List<Bird> = emptyList(),
    val selectedCategory: String? = null,
) {
    val categories: Set<String> = birds.map {
        it.category
    }.toSet()
    val selectedBirds = birds.filter { it.category == selectedCategory }
}
