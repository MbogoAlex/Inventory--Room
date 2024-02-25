package com.example.inventory.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.ui.ApplicationViewModelFactory
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object ItemDetailsDestination: NavigationDestination {
    override val route: String = "item_details"
    override val titleRes: Int = R.string.item_detail_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"

}

@Composable
fun ItemDetailsScreen(
//    onSellButtonClicked: () -> Unit,
//    onDeleteButtonClicked: () -> Unit,
    navigateBack: () -> Unit,
    navigateToEditDetailsScreen: (itemId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ItemDetailsViewModel = viewModel(factory = ApplicationViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
                 ItemDetailsAppBar(
                     navigateBack = navigateBack
                 )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToEditDetailsScreen(uiState.itemDetails.itemId)
                },

            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit item"
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                ItemDetailsCard(
                    itemDetails = uiState.itemDetails

                    )
                Spacer(modifier = Modifier.height(20.dp))
                ItemDetailsButtons(
                    outOfStock = uiState.outOfStock,
                    onSellButtonClicked = { viewModel.sellItem() },
                    onDeleteButtonClicked = {
                        viewModel.deleteItem()
                        navigateBack()
                    }
                )
            }
        }
    }
}

@Composable
fun ItemDetailsCard(
    itemDetails: ItemDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Item")
                Text(
                    text = itemDetails.name,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Quantity in stock")
                Text(
                    text = itemDetails.quantity,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Price")
                Text(
                    text = itemDetails.price,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ItemDetailsButtons(
    outOfStock: Boolean,
    onSellButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Button(
            enabled = !outOfStock,
            onClick = onSellButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Sell")
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = onDeleteButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Delete")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsAppBar(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Item Details")
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview(
    modifier: Modifier = Modifier
) {
    InventoryTheme {
        ItemDetailsScreen(
            navigateBack = {},
            navigateToEditDetailsScreen = {itemId ->}
        )
    }
}