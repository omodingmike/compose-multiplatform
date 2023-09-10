import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Bird
import org.jetbrains.compose.resources.ExperimentalResourceApi
import viewmodel.BirdsViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        val myValue by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ) {
            BadgedBox(
                badge = {
                    Badge {
                        Text(
                            text = "40",
                            fontSize = 12.sp
                        )
                    }
                }) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Favorite"
                )
            }
//            BirdsPage(birdsViewModel = birdsViewModel)
        }
    }
}


@Composable
fun BirdsPage(birdsViewModel: BirdsViewModel) {
    val uiState by birdsViewModel.uiState.collectAsState()
//    Row {
//        for (category in uiState.categories) {
//            Button(
//                modifier = Modifier
//                    .aspectRatio(1.0f)
//                    .fillMaxSize().weight(1.0f),
//                onClick = {
//                    birdsViewModel.selectCategory(category = category)
//                }
//            ) {
//                Text(text = "mike")
//            }
//        }
//    }
    AnimatedVisibility(uiState.birds.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            content = {
                items(uiState.birds) {
                    BirdImageCell(it)
                }
            }
        )
    }
}

@Composable
fun BirdImageCell(bird: Bird) {
    KamelImage(
        asyncPainterResource("https://sebi.io/demo-image-api/${bird.path}"),
        null, contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
    )
}

expect fun getPlatformName(): String