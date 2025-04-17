package com.example.quotegen.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quote: String,
    val author: String,
    val category: String
)

data class QuoteResponse(
    val quote: String,
    val author: String,
    val category: String
)

fun QuoteResponse.toQuote() = Quote(
    quote = quote,
    author = author,
    category = category
) 