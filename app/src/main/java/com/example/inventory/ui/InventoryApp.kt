package com.example.inventory.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.ui.navigation.InventoryNavHost

@Composable
fun InventoryApp(
    navController: NavHostController = rememberNavController(),
    
) {
    InventoryNavHost(navController = navController)
}