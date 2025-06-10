package com.example.azbuka.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.azbuka.data.local.dao.CardDao
import com.example.azbuka.data.local.entity.CardEntity
import com.example.azbuka.domain.model.DayStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_progress")

class ProgressRepository(
    private val context: Context,
    private val userId: String,
    private val dao: CardDao
) {
    suspend fun getAllCards(): List<CardEntity> = dao.getAllCards()

    private fun userKey(key: String) = "${userId}_$key"
    private fun intKey(name: String) = intPreferencesKey(userKey(name))

    val finalLearnedKey get() = intKey("final_learned_count")
    val finalTotalKey get() = intKey("final_total_count")
    val weeklyLearnedKey get() = intKey("weekly_learned_total")


    val activeKeys: Map<Int, Preferences.Key<Int>> = (1..7).associateWith {
        intKey("day_active_$it")
    }
    val learnedKeys: Map<Int, Preferences.Key<Int>> = (1..7).associateWith {
        intKey("day_learned_$it")
    }

    val finalLearnedCountFlow: Flow<Int> = context.dataStore.data
        .map { it[finalLearnedKey] ?: 0 }

    val finalTotalCardsFlow: Flow<Int> = context.dataStore.data
        .map { it[finalTotalKey] ?: 0 }

    suspend fun saveFinalSession(learned: Int, total: Int) {
        context.dataStore.edit {
            it[finalLearnedKey] = learned
            it[finalTotalKey] = total
            it[weeklyLearnedKey] = (it[weeklyLearnedKey] ?: 0) + learned
        }
    }

    suspend fun loadWeeklyActivity(): Map<Int, DayStat> {
        val prefs = context.dataStore.data.first()
        val finalTotal = prefs[finalTotalKey] ?: 0

        return (1..7).associateWith { day ->
            val active = prefs[activeKeys[day]!!] == 1
            val learned = prefs[learnedKeys[day]!!] ?: 0
            val remaining = (finalTotal - learned).coerceAtLeast(0)
            DayStat(active, learned, remaining)
        }
    }

    suspend fun saveWeeklyActivity(activityMap: Map<Int, DayStat>) {
        context.dataStore.edit { prefs ->
            activityMap.forEach { (day, stat) ->
                prefs[activeKeys[day]!!] = if (stat.active) 1 else 0
                prefs[learnedKeys[day]!!] = stat.learned
            }
        }
    }
}
