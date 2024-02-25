package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItemStream(itemId: Int): Flow<Item>
    fun getAllItemsStream(): Flow<List<Item>>
    suspend fun insertItem(item: Item)
    suspend fun updateItem(item: Item)
    suspend fun deleteItem(item: Item)
}

class OfflineItemsRepository(private val itemDao: ItemDao) : ItemsRepository {
    override fun getItemStream(itemId: Int): Flow<Item> = itemDao.getItem(itemId)

    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override suspend fun insertItem(item: Item) = itemDao.insertItem(item)

    override suspend fun updateItem(item: Item) = itemDao.updateItem(item)

    override suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)

}