package com.google.codelabs.buildyourfirstmap

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.codelabs.buildyourfirstmap.models.BoardSize
import com.google.codelabs.buildyourfirstmap.models.MemoryCard
import com.google.codelabs.buildyourfirstmap.models.MemoryGameLogic
import com.google.codelabs.buildyourfirstmap.utils.DEFAULT_ICONS
import kotlinx.android.synthetic.main.activity_memory_game.*
import kotlinx.android.synthetic.main.dialog_board_size.view.*

class MemoryGame : AppCompatActivity() {

    companion object {
        private const val TAG = "MemoryGame"
    }

    private lateinit var clRoot : CoordinatorLayout
    private lateinit var rvBoard : RecyclerView
    private lateinit var tvNumMoves : TextView
    private lateinit var tvNumPairs : TextView

    private lateinit var memoryGameLogic: MemoryGameLogic
    private lateinit var adapter: MemoryBoardAdapter
    // initialise the difficulty level
    private var boardSize : BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        // calling the action bar
        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        setupBoard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memory, menu)
        return true
    }

    // this event will enable the back function to the button on press

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_refresh -> {
                // refresh/setup the game again
                if (memoryGameLogic.getNumMoves() > 0 && !memoryGameLogic.haveWonGame()) {
                    showAlertDialog("Quit your current game round?", null, View.OnClickListener {
                        setupBoard()
                    })
                } else {
                    setupBoard()
                }
                return true
            }

            R.id.mi_difficulty -> {
                showNewDifficultyDialog()
                return true
            }
            // this event will enable the back function to the button on press
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNewDifficultyDialog() {
        val boardSizeView = LayoutInflater.from(this).inflate(R.layout.dialog_board_size, null)
        val radioDifficultyLevel = boardSizeView.findViewById<RadioGroup>(R.id.radioDifficultyLevel)
        when (boardSize) {
            BoardSize.EASY -> radioDifficultyLevel.check(R.id.rbEasy)
            BoardSize.MEDIUM -> radioDifficultyLevel.check(R.id.rbMedium)
            BoardSize.HARD -> radioDifficultyLevel.check(R.id.rbHard)
        }
        showAlertDialog("Choose difficulty level", boardSizeView, View.OnClickListener {
            // Set new value for the board size
            // Easy : 4 x 2, Medium: 6 x 3, Hard: 6 x 4
            boardSize = when (radioDifficultyLevel.checkedRadioButtonId) {
                R.id.rbEasy -> BoardSize.EASY
                R.id.rbMedium -> BoardSize.MEDIUM
                else -> BoardSize.HARD
            }
            setupBoard()
        })
    }

    private fun showAlertDialog(title: String, view: View?, positiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel", null)
            // null means dismiss alert dialog if users clicks on cancel
            .setPositiveButton("Ok") { _, _ ->
                positiveClickListener.onClick(null)
            }.show()
    }

    private fun setupBoard() {
        // it will remind the user what game (mode) they are playing in tvNumMoves,
        // then game proceeds as usual
        when (boardSize) {
            BoardSize.EASY -> {
                tvNumMoves.text = "Easy"
                tvNumPairs.text = "Pairs: 0 / 4"
            }
            BoardSize.MEDIUM -> {
                tvNumMoves.text = "Medium"
                tvNumPairs.text = "Pairs: 0 / 9"
            }
            BoardSize.HARD -> {
                tvNumMoves.text = "Hard"
                tvNumPairs.text = "Pairs: 0 / 12"
            }
        }
        tvNumPairs.setTextColor(ContextCompat.getColor(this, R.color.color_progress_none))

        memoryGameLogic = MemoryGameLogic(boardSize)

        adapter = MemoryBoardAdapter(this, boardSize, memoryGameLogic.cards, object: MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                updateGameWithFlip(position)
            }
        })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        // Error checking
        if (memoryGameLogic.haveWonGame()) {
            // Alert the user that they already won and cannot flip any more cards
            Snackbar.make(clRoot, "You already won!", Snackbar.LENGTH_LONG)
                .setAnchorView(tvNumMoves)
                .show()
            return
        }
        if (memoryGameLogic.isCardFaceUp(position)) {
            // Alert the user of an invalid move
            Snackbar.make(clRoot, "Invalid move!", Snackbar.LENGTH_SHORT)
                .setAnchorView(tvNumMoves)
                .show()
            return
        }
        // Actually flipping over the card
        if (memoryGameLogic.flipCard(position)) {
            Log.i(TAG, "Found a match! Number of pairs found: ${memoryGameLogic.numPairsFound}")
            // to show progress of number of pairs found visually
            val color = ArgbEvaluator().evaluate(
                memoryGameLogic.numPairsFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this, R.color.color_progress_none),
                ContextCompat.getColor(this, R.color.color_progress_full)
            ) as Int
            tvNumPairs.setTextColor(color)
            tvNumPairs.text = "Pairs: ${memoryGameLogic.numPairsFound} / ${boardSize.getNumPairs()}"
            if (memoryGameLogic.haveWonGame()) {
                Snackbar.make(clRoot, "You won! Congratulations :)", Snackbar.LENGTH_LONG)
                    .setAnchorView(tvNumMoves)
                    .show()
            }
        }
        // update textview to show how many moves user has made
        tvNumMoves.text = "Moves: ${memoryGameLogic.getNumMoves()}"
        adapter.notifyDataSetChanged()
    }
}