package com.example.inventory.ui.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.R
import com.example.inventory.ui.ApplicationViewModelFactory
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

object ItemEditDestination: NavigationDestination {
    override val route: String = "item_edit"
    override val titleRes: Int = R.string.item_edit_title
    val itemIdArg = "itemId"
    val routeArgs = "$route/{$itemIdArg}"
}

@Composable
fun ItemEditScreen(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ItemEditViewModel = viewModel(factory = ApplicationViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            ItemEditAppBar(
                onBackButtonClicked = onBackButtonClicked
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                EditTextField(
                    valueText = uiState.itemDetails.name,
                    labelText = uiState.itemDetails.name,
                    onValueChange = { name ->
                        viewModel.updateUiState(
                            itemDetails = uiState.itemDetails.copy(
                                name = name
                            )
                        )
                    }
                )
                EditTextField(
                    valueText = uiState.itemDetails.quantity,
                    labelText = uiState.itemDetails.quantity,
                    onValueChange = {quantity ->
                        viewModel.updateUiState(
                            itemDetails = uiState.itemDetails.copy(
                                quantity = quantity
                            )
                        )
                    }
                )
                EditTextField(
                    valueText = uiState.itemDetails.price,
                    labelText = uiState.itemDetails.price,
                    onValueChange = {price ->
                        viewModel.updateUiState(
                            itemDetails = uiState.itemDetails.copy(
                                price = price
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                SaveButton(
                    showButton = uiState.showSaveButton,
                    onSaveEdit = {
                        viewModel.saveEdit(uiState.itemDetails)
                        onBackButtonClicked()
                    }
                )
            }
        }
    }
}

@Composable
fun EditTextField(
    valueText: String,
    labelText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = valueText,
            label = {
                Text(text = labelText)
            },
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun SaveButton(
    showButton: Boolean,
    onSaveEdit: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        enabled = showButton,
        onClick = onSaveEdit,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Save")

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditAppBar(
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Edit item")
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClicked
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
fun ItemEditScreenPreview(
    modifier: Modifier = Modifier
) {
    InventoryTheme {
        ItemEditScreen(
            onBackButtonClicked = {}
        )
    }
}