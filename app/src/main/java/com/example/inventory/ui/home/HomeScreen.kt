package com.example.inventory.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.ApplicationViewModelFactory
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToItemDetails: (itemId: Int) -> Unit,
    navigateToItemAddScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeScreenViewModel = viewModel(factory = ApplicationViewModelFactory.Factory)
    val uiState by viewModel.homeUiState.collectAsState()
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemAddScreen) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
        }
    ) {
        ItemsDisplay(
            items = uiState.items,
            navigateToItemDetails = navigateToItemDetails,
            modifier = Modifier
                .padding(it)
        )
    }
}

@Composable
fun ItemsDisplay(
    items: List<Item>,
    navigateToItemDetails: (itemId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items) { item ->
            InventoryItem(
                itemDetails = item,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        navigateToItemDetails(item.itemId)
                    }
            )

        }
    }
}

@Composable
fun InventoryItem(
    itemDetails: Item,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(text = itemDetails.name)
                Text(text = itemDetails.price.toString())
            }
            Text(text = itemDetails.quantity.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    InventoryTheme {
        HomeScreen(
            navigateToItemDetails = {itemId ->},
            navigateToItemAddScreen = {}
        )
    }
}

