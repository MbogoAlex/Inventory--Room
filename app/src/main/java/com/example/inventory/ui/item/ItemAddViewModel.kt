package com.example.inventory.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemAddUiState(
    val itemDetails: ItemDetails = ItemDetails(0, "", "", ""),
    val showSaveButton: Boolean = false
)

class ItemAddViewModel(private val itemsRepository: ItemsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(value = ItemAddUiState())
    val itemAddUiState: StateFlow<ItemAddUiState> = _uiState.asStateFlow()

    fun updateUiState(itemDetails: ItemDetails) {
        _uiState.update {
            it.copy(
                itemDetails = itemDetails,
                showSaveButton = validateItems(itemDetails)
            )
        }
    }

    fun validateItems(itemDetails: ItemDetails = _uiState.value.itemDetails): Boolean {
        return with(itemDetails) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    suspend fun saveItem() {
        itemsRepository.insertItem(_uiState.value.itemDetails.toItem())

    }



}

data class ItemDetails(
    val itemId: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

fun ItemDetails.toItem(): Item = Item (
    itemId = itemId,
    name = name,
    price = price.toDouble(),
    quantity = quantity.toInt()
)
