package com.example.azbuka.domain.model

data class DayStat(
    val active: Boolean,
    val learned: Int,
    val remaining: Int = 0
)
