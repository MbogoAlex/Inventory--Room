package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemAddDestination
import com.example.inventory.ui.item.ItemAddScreen
import com.example.inventory.ui.item.ItemDetails
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemDetails = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                },
                navigateToItemAddScreen = {
                    navController.navigate(ItemAddDestination.route)
                }
            )
        }
        composable(route = ItemAddDestination.route) {
            ItemAddScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg){
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateBack = {navController.navigateUp()},
                navigateToEditDetailsScreen = {
                    navController.navigate("${ItemEditDestination.route}/${it}")
                }
            )
        }
        composable(
            route = ItemEditDestination.routeArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg){
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(onBackButtonClicked = { navController.navigateUp() })
        }
    }
}