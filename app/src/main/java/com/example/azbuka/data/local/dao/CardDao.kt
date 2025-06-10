package com.example.azbuka.data.local.dao

import androidx.room.*
import com.example.azbuka.data.local.entity.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM card_table")
    suspend fun getAllCards(): List<CardEntity>

    @Query("SELECT * FROM card_table WHERE isLearned = 0")
    suspend fun getUnlearnedCards(): List<CardEntity>
}
