package com.google.codelabs.buildyourfirstmap.models

import com.google.codelabs.buildyourfirstmap.utils.DEFAULT_ICONS

class MemoryGameLogic(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var numCardFlips = 0
    private var indexOfSingleSelectedCard: Int? = null

    init {
        // need to randomise so the images do not appear in the same location again
        // randomise the list of icons and take a certain number of images
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        // so each image there appears twice (need a pair of images), and again randomizing the list with shuffled()
        val randomisedImages = (chosenImages + chosenImages).shuffled()
        // each randomised image will correspond to one memory card and we want to create of these memory cards
        // the map function allows the element of randomisedImages to do an operation and create a
        // new list (transform randomisedImages into a new list, in particular, create a new memory card object)
        // it refers to the current randomised image that we are mapping over
        cards = randomisedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int) : Boolean {
        numCardFlips++
        val card = cards[position]
        // Three cases:
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards (make them face down again) + flip over the selected card
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        }
        else {
            // exactly 1 card previously flipped over
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            // after the user flipped the cards, there will no longer be exactly 1 card flipped over
            // thats why the index is set to null
            indexOfSingleSelectedCard = null
        }
        // the opposite of what it was
        // e.g if card was face down before, its gonna be face up
        // and vice-versa
        card.isFaceUp = !card.isFaceUp
        // to return a Boolean on whether a match was found or not
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            // user picked incorrectly
            return false
        }
        // if both cards matched
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            // if card is not matched, card faces down
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        // won game if number of pairs found is equal to the total number of pairs available
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        // 2 card flips = 1 move
        return numCardFlips / 2
    }
}