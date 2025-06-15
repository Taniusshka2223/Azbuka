package com.example.azbuka.ui.navs.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.azbuka.data.local.AppDatabase
import com.example.azbuka.data.local.entity.CardEntity
import com.example.azbuka.data.repository.ProgressRepository
import com.example.azbuka.domain.model.DayStat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid
        ?: error("Пользователь должен пройти авторизацию")

    private val repository: ProgressRepository

    private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val cards: StateFlow<List<CardEntity>> = _cards.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _repeatCount = MutableStateFlow(0)
    val repeatCount: StateFlow<Int> = _repeatCount.asStateFlow()

    private val _learnedCount = MutableStateFlow(0)
    val learnedCount: StateFlow<Int> = _learnedCount.asStateFlow()

    private val _totalCards = MutableStateFlow(0)

    private val _finalLearned = MutableStateFlow(0)
    val finalLearned: StateFlow<Int> = _finalLearned.asStateFlow()

    private val _finalTotal = MutableStateFlow(0)
    val finalTotal: StateFlow<Int> = _finalTotal.asStateFlow()

    private val _weeklyActivity = MutableStateFlow<Map<Int, DayStat>>(emptyMap())
    val weeklyActivity: StateFlow<Map<Int, DayStat>> = _weeklyActivity.asStateFlow()

    private val _activeDaysCount = MutableStateFlow(0)

    init {
        val dao = AppDatabase.getInstance(application).cardDao()
        repository = ProgressRepository(application, userId, dao)

        loadCards()
        collectFlow(repository.finalLearnedCountFlow) { _finalLearned.value = it }
        collectFlow(repository.finalTotalCardsFlow) { _finalTotal.value = it }
        loadWeeklyActivity()
    }

    private fun <T> collectFlow(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewModelScope.launch {
            flow.collect { action(it) }
        }
    }

    fun loadCards() {
        viewModelScope.launch {
            _cards.value = repository.getAllCards()
        }
    }

    fun loadWeeklyActivity() {
        viewModelScope.launch {
            val stored = repository.loadWeeklyActivity()
            _weeklyActivity.value = stored
            _activeDaysCount.value = stored.values.count { it.active }
        }
    }

    fun updateIndex(newIndex: Int) { _currentIndex.value = newIndex }

    fun updateRepeat(newCount: Int) { _repeatCount.value = newCount }

    fun updateLearned(count: Int) { _learnedCount.value = count }

    fun updateTotal(count: Int) { _totalCards.value = count }

    fun finishSession() {
        val today = getCurrentDayOfWeek()
        val learned = _learnedCount.value
        val total = _totalCards.value
        val remaining = (total - learned).coerceAtLeast(0)

        viewModelScope.launch {
            repository.saveFinalSession(learned, total)

            val updated = _weeklyActivity.value.toMutableMap()
            updated[today] = DayStat(true, learned, remaining)

            _weeklyActivity.value = updated
            _activeDaysCount.value = updated.values.count { it.active }

            repository.saveWeeklyActivity(updated)
        }
    }

    private fun getCurrentDayOfWeek(): Int {
        val calendarDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return if (calendarDay == Calendar.SUNDAY) 7 else calendarDay - 1
    }
}