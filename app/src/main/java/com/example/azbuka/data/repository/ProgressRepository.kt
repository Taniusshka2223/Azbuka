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

    suspend fun getUnlearnedCards(): List<CardEntity> = dao.getUnlearnedCards()

    suspend fun insertCard(card: CardEntity) = dao.insertCard(card)

    suspend fun updateCard(card: CardEntity) = dao.updateCard(card)

    suspend fun deleteCard(card: CardEntity) = dao.deleteCard(card)

    private fun userKey(key: String) = "${userId}_$key"
    private fun intKey(name: String) = intPreferencesKey(userKey(name))

    val FINAL_LEARNED_KEY get() = intKey("final_learned_count")
    val FINAL_TOTAL_KEY get() = intKey("final_total_count")
    val WEEKLY_LEARNED_KEY get() = intKey("weekly_learned_total")
    val WEEKLY_GOAL_KEY get() = intKey("weekly_goal")

    val ACTIVE_KEYS: Map<Int, Preferences.Key<Int>> = (1..7).associateWith {
        intKey("day_active_$it")
    }
    val LEARNED_KEYS: Map<Int, Preferences.Key<Int>> = (1..7).associateWith {
        intKey("day_learned_$it")
    }

    val finalLearnedCountFlow: Flow<Int> = context.dataStore.data
        .map { it[FINAL_LEARNED_KEY] ?: 0 }

    val finalTotalCardsFlow: Flow<Int> = context.dataStore.data
        .map { it[FINAL_TOTAL_KEY] ?: 0 }

    val weeklyLearnedFlow: Flow<Int> = context.dataStore.data
        .map { it[WEEKLY_LEARNED_KEY] ?: 0 }

    val weeklyGoalFlow: Flow<Int> = context.dataStore.data
        .map { it[WEEKLY_GOAL_KEY] ?: 20 }

    suspend fun saveFinalSession(learned: Int, total: Int) {
        context.dataStore.edit {
            it[FINAL_LEARNED_KEY] = learned
            it[FINAL_TOTAL_KEY] = total
            it[WEEKLY_LEARNED_KEY] = (it[WEEKLY_LEARNED_KEY] ?: 0) + learned
        }
    }

    suspend fun loadWeeklyActivity(): Map<Int, DayStat> {
        val prefs = context.dataStore.data.first()
        val finalTotal = prefs[FINAL_TOTAL_KEY] ?: 0

        return (1..7).associateWith { day ->
            val active = prefs[ACTIVE_KEYS[day]!!] == 1
            val learned = prefs[LEARNED_KEYS[day]!!] ?: 0
            val remaining = (finalTotal - learned).coerceAtLeast(0)
            DayStat(active, learned, remaining)
        }
    }

    suspend fun saveWeeklyActivity(activityMap: Map<Int, DayStat>) {
        context.dataStore.edit { prefs ->
            activityMap.forEach { (day, stat) ->
                prefs[ACTIVE_KEYS[day]!!] = if (stat.active) 1 else 0
                prefs[LEARNED_KEYS[day]!!] = stat.learned
            }
        }
    }

    suspend fun resetWeeklyProgress() {
        context.dataStore.edit {
            it[WEEKLY_LEARNED_KEY] = 0
            (1..7).forEach { day ->
                it[LEARNED_KEYS[day]!!] = 0
            }
        }
    }

    suspend fun setWeeklyGoal(goal: Int) {
        context.dataStore.edit {
            it[WEEKLY_GOAL_KEY] = goal
        }
    }

    suspend fun getWeeklyProgress(): Pair<Int, Int> {
        val prefs = context.dataStore.data.first()
        val learned = prefs[WEEKLY_LEARNED_KEY] ?: 0
        val goal = prefs[WEEKLY_GOAL_KEY] ?: 20
        return Pair(learned, goal)
    }
}
