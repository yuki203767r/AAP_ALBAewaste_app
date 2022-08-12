package com.google.codelabs.buildyourfirstmap.models

data class MemoryCard(
    // the identifier is the drawable resource that we defined earlier
    val identifier: Int,
    // all memory cards start face down
    var isFaceUp: Boolean = false,
    // if the memory card has found its corresponding pair
    var isMatched: Boolean = false
)