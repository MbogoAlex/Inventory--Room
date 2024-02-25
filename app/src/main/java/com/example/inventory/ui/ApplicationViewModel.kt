package com.example.inventory.ui


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeScreenViewModel
import com.example.inventory.ui.item.ItemAddViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel

object ApplicationViewModelFactory {
    val Factory = viewModelFactory {
        // initialize for HomeScreenViewModel

        initializer {
            val itemsRepository = inventoryApplication().container.itemsRepository
            HomeScreenViewModel(itemsRepository = itemsRepository)
        }

        //initialize for ItemAddViewModel
        initializer {
            val itemsRepository = inventoryApplication().container.itemsRepository
            ItemAddViewModel(itemsRepository = itemsRepository)
        }

        //initialize for ItemDetailsViewModel
        initializer {
            val itemsRepository = inventoryApplication().container.itemsRepository
            ItemDetailsViewModel(
                itemsRepository = itemsRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer {
            val itemsRepository = inventoryApplication().container.itemsRepository
            ItemEditViewModel(
                itemsRepository = itemsRepository,
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }


}

fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)

