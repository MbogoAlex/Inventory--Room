package com.example.inventory.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.ui.ApplicationViewModelFactory
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme
import kotlinx.coroutines.launch

object ItemAddDestination: NavigationDestination {
    override val route: String = "item_entry"
    override val titleRes: Int = R.string.item_entry_tile
}
@Composable
fun ItemAddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ItemAddViewModel = viewModel(factory = ApplicationViewModelFactory.Factory)
    val uiState by viewModel.itemAddUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ItemAddTopBar(
                onUpButtonClicked = navigateBack
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                //name text field
                AddTextField(
                    labelText = "Name",
                    value = uiState.itemDetails.name,
                    itemDetails = uiState.itemDetails,
                    onValueChange = {name ->
                        viewModel.updateUiState(
                            uiState.itemDetails.copy(name = name)
                        )

                         },
                        modifier = Modifier
                            .padding(
                                top = 10.dp)
                    )
                    //price text field
                    AddTextField(
                        labelText = "Price",
                        value = uiState.itemDetails.price,
                        itemDetails = uiState.itemDetails,
                        onValueChange = {price ->
                            viewModel.updateUiState(
                                uiState.itemDetails.copy(price = price)
                            )
                        },
                        modifier = Modifier
                            .padding(
                                top = 10.dp
                            )
                    )
                    //Quantity text field
                    AddTextField(
                        labelText = "Quantity",
                        value = uiState.itemDetails.quantity,
                        itemDetails = uiState.itemDetails,
                        onValueChange = {quantity ->
                            viewModel.updateUiState(
                                uiState.itemDetails.copy(
                                    quantity = quantity
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(
                                top = 10.dp
                            )
                    )

                    //Save button
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveItem()
                            }
                            navigateBack()
                                  },
                        enabled = uiState.showSaveButton,
                        modifier = Modifier
                            .widthIn(150.dp)
                            .padding(
                                top = 10.dp
                            )
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }

}

@Composable
fun AddTextField(
    labelText: String,
    value: String,
    itemDetails: ItemDetails,
    onValueChange: (newValue: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        OutlinedTextField(
            label = {
                Text(text = labelText)
            },

            value = value,
            onValueChange = onValueChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddTopBar(
    onUpButtonClicked: () ->Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Add new item")
        },
        navigationIcon = {
            IconButton(
                onClick = onUpButtonClicked
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
fun ItemAddScreenPreview() {
    InventoryTheme {
        ItemAddScreen(
            navigateBack = {}
        )
    }
}