package com.ctseducare.expensebook.model

import com.ctseducare.expensebook.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Expense(
    var id: Int? = null,
    var description: String,
    var value: Double,
    @Serializable(with = LocalDateSerializer::class)
    var paid_at: LocalDate
)
