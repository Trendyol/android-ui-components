package com.trendyol.uicomponents.samplecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.trendyol.uicomponents.samplecompose.ui.theme.ColorPrimary
import com.trendyol.uicomponents.shimmercompose.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShimmerScreen(viewModel: ShimmerViewModel = viewModel()) = with(viewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = ColorPrimary,
                elevation = 0.dp
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Shimmer",
                    style = MaterialTheme.typography.h6.copy(color = Color.White)
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(20) {
                ListItem(uiState.item, uiState.isLoading)
            }
        }
    }
}

@Composable
fun ListItem(item: Item, isLoading: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .shimmer(isLoading)
        )

        Text(
            text = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(isLoading),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Stable
interface ShimmerUiState {
    val item: Item
    val isLoading: Boolean
}

@Stable
data class Item(
    val title: String,
    val imageUrl: String
)

class MutableShimmerUiState : ShimmerUiState {
    override var item: Item by mutableStateOf(Item("", ""))
    override var isLoading: Boolean by mutableStateOf(true)
}

class ShimmerViewModel : ViewModel() {
    private val _uiState = MutableShimmerUiState()
    val uiState: ShimmerUiState = _uiState

    init {
        fetchItem()
    }

    private fun fetchItem() = viewModelScope.launch {
        delay(1_500)
        _uiState.isLoading = false
        _uiState.item = Item(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "https://via.placeholder.com/600/92c952"
        )
    }
}