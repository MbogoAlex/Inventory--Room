package com.example.inventory.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemEditUiState(
    val itemDetails: ItemDetails = ItemDetails(0, "", "", ""),
    val showSaveButton: Boolean = false,
)

class ItemEditViewModel(
    private val itemsRepository: ItemsRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = ItemEditUiState())
    val uiState: StateFlow<ItemEditUiState> = _uiState.asStateFlow()
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    fun loadItem() {
        viewModelScope.launch {
            itemsRepository.getItemStream(itemId).onEach {
                _uiState.value = ItemEditUiState(
                    it.toItemDetails()
                )
            }
                .launchIn(this)
        }
    }

    init {
        loadItem()
    }

    fun updateUiState(itemDetails: ItemDetails = _uiState.value.itemDetails) {
        val valid =  with(itemDetails) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
        _uiState.update {
            it.copy(
                itemDetails = itemDetails,
                showSaveButton = valid
            )
        }
    }

    fun saveEdit(itemDetails: ItemDetails) {
        viewModelScope.launch {
            itemsRepository.updateItem(itemDetails.toItem())
        }
    }

}