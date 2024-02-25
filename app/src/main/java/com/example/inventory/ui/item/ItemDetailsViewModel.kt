package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class ItemDetailsUiState (
    val itemDetails: ItemDetails = ItemDetails(
        1,
        "",
        "",
        ""
    ),
    val outOfStock: Boolean = false,
)



class ItemDetailsViewModel(
    private val itemsRepository: ItemsRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = ItemDetailsUiState())
    val uiState: StateFlow<ItemDetailsUiState> = _uiState.asStateFlow()
    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    fun fetchItemDetails() {
         viewModelScope.launch {
             itemsRepository.getItemStream(itemId)
                 .map {
                     ItemDetailsUiState(
                         it.toItemDetails(),
                         outOfStock = it.quantity <= 0,
                     )
                 }
                 .onEach {
                     _uiState.value = it
                 }
                 .launchIn(this)
         }

    }

    init {
        fetchItemDetails()
    }

    fun sellItem() {
        viewModelScope.launch {
            itemsRepository.updateItem(
                item = _uiState.value.itemDetails.toItem().copy(
                    quantity = _uiState.value.itemDetails.quantity.toInt() - 1
                )
            )
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            itemsRepository.deleteItem(uiState.value.itemDetails.toItem())
        }
    }
}

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    itemId = itemId,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)



