package com.example.inventory.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val items: List<Item> = listOf()
)

class HomeScreenViewModel(private val itemsRepository: ItemsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(value = HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun getItems() {
        viewModelScope.launch {
            itemsRepository.getAllItemsStream()
                .map { HomeUiState(it) }
                .onEach {
                    Log.i("IT_ITEMS", it.toString())
                    _uiState.value = it
                }
                .launchIn(this) // launch the flow in the ViewModel's scope
        }
    }


    init {
        getItems()
    }

}
