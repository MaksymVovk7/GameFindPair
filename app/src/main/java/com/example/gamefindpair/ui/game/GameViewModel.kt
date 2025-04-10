package com.example.gamefindpair.ui.game

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamefindpair.R
import com.example.gamefindpair.data.model.Card

class GameViewModel(
    private val cardCount: Int
): ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long> = _time

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean> = _gameFinished

    private var selectedCards = mutableListOf<Card>()
    private var matchedPairs = 0

    private var startTime = 0L
    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            val elapsed = System.currentTimeMillis() - startTime
            _time.postValue(elapsed)
            timerHandler.postDelayed(this, 1000)
        }
    }

    init {
        resetGame()
    }

    fun resetGame() {
        val generatedCards = generateShuffledCards(cardCount)
        _cards.value = generatedCards
        matchedPairs = 0
        selectedCards.clear()
        startTime = System.currentTimeMillis()
        timerHandler.post(timerRunnable)
        _gameFinished.value = false
    }

    private fun generateShuffledCards(count: Int): List<Card> {
        val imageIds = listOf(
            R.drawable.apple_icon, R.drawable.bananas_icon, R.drawable.cherries_icon,
            R.drawable.dragon_fruit_icon, R.drawable.grapes_icon, R.drawable.lemon_icon,
            R.drawable.orange_icon, R.drawable.passion_fruit_icon, R.drawable.strawberry_icon,
            R.drawable.watermelon_icon
        ).shuffled().take(count / 2)

        val cards = (imageIds + imageIds).mapIndexed { index, id ->
            Card(id, index)
        }
        return cards.shuffled()
    }

    fun onCardClicked(card: Card) {
        if (card.isFaceUp || selectedCards.size == 2) return

        val currentCards = _cards.value?.toMutableList() ?: return
        val clickedCard = currentCards.find { it.id == card.id } ?: return
        clickedCard.isFaceUp = true
        selectedCards.add(clickedCard)
        _cards.value = currentCards

        if (selectedCards.size == 2) {
            timerHandler.postDelayed({
                checkForMatch()
            }, 500)
        }
    }

    private fun checkForMatch() {
        val currentCards = _cards.value?.toMutableList() ?: return
        val (first, second) = selectedCards

        if (first.imageResId == second.imageResId) {
            matchedPairs++
            if (matchedPairs == cardCount / 2) {
                _gameFinished.value = true
                timerHandler.removeCallbacks(timerRunnable)
            }
        } else {
            currentCards.find { it.id == first.id }?.isFaceUp = false
            currentCards.find { it.id == second.id }?.isFaceUp = false
        }

        _cards.value = currentCards
        selectedCards.clear()
    }

    fun getElapsedTime(): Long {
        return System.currentTimeMillis() - startTime
    }
}