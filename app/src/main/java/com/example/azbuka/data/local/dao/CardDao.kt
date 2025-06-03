package com.example.azbuka.data.local.dao

import androidx.room.*
import com.example.azbuka.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM card_table")
    suspend fun getAllCards(): List<CardEntity>

    @Query("SELECT * FROM card_table WHERE isLearned = 0")
    suspend fun getUnlearnedCards(): List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)
}
