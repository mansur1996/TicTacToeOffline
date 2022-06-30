package com.mrmansur.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.mrmansur.tictactoe.databinding.ActivityMainBinding
import com.mrmansur.tictactoe.databinding.WinDialogLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isXTurn = true
    private var xCount = 0
    private var oCount = 0
    private var drawCount = 0
    private var count = 0
    private val chars = Array(3) { IntArray(3) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        setTurnTitle()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnFirst.setOnClickListener {
                btnClicked(it as Button, 0)
            }
            btnSecond.setOnClickListener {
                btnClicked(it as Button, 1)
            }
            btnThird.setOnClickListener {
                btnClicked(it as Button, 2)
            }
            btnFourth.setOnClickListener {
                btnClicked(it as Button, 3)
            }
            btnFifth.setOnClickListener {
                btnClicked(it as Button, 4)
            }
            btnSixth.setOnClickListener {
                btnClicked(it as Button, 5)
            }
            btnSeventh.setOnClickListener {
                btnClicked(it as Button, 6)
            }
            btnEighth.setOnClickListener {
                btnClicked(it as Button, 7)
            }
            btnNinth.setOnClickListener {
                btnClicked(it as Button, 8)
            }
        }
    }

    private fun btnClicked(button: Button, buttonNumber : Int) {
        if (button.text.isNotBlank()) return
        button.text = if (isXTurn) {
            button.setTextColor(Color.RED)
            chars[buttonNumber / 3][buttonNumber % 3] = 1 // x->1
            "X"
        } else {
            button.setTextColor(Color.GREEN)
            chars[buttonNumber / 3][buttonNumber % 3] = -1 // o->-1
            "O"
        }
        count++
        checkWin(count)
        isXTurn = !isXTurn
        setTurnTitle()
    }

    private fun setTurnTitle() {
        binding.tvTurnTitle.text = if (isXTurn) {
            getString(R.string.turn_x_player)
        } else {
            getString(R.string.turn_o_player)
        }
    }

    private fun checkWin(count : Int) {

        // Horizontal
        for (row in chars){
            val sum = row[0] + row[1] + row[2]
            if (sum == 3){
                showWinDialog('X')
                return
            }else if (sum == -3) {
                showWinDialog('O')
                return
            }
        }

        //Vertical
        for (i in 0..2){
            val sum = chars[0][i] + chars[1][i] + chars[2][i]
            if (sum == 3){
                showWinDialog('X')
                return
            }else if (sum == -3) {
                showWinDialog('O')
                return
            }
        }

        //Main Diagonal
        var sum = chars[0][0] + chars[1][1] + chars[2][2]
        if(sum == 3){
            showWinDialog('X')
            return
        }else if (sum == -3){
            showWinDialog('O')
            return
        }

        //Second Diagonal
        sum = chars[2][0] + chars[1][1] + chars[0][2]
        if(sum == 3){
            showWinDialog('X')
            return
        }else if (sum == -3){
            showWinDialog('O')
            return
        }
        if (count == 9){
            showWinDialog('*')
            return
        }

    }

    private fun showWinDialog(char: Char) {
        val dialogLayoutBinding = WinDialogLayoutBinding.inflate(LayoutInflater.from(this))
        dialogLayoutBinding.textView.text = if (char == 'X'){
            xCount++
            binding.tvXWonCount.text = "$xCount"
            "X player won"
        }else if (char == 'O'){
            oCount++
            binding.tvOWonCount.text = oCount.toString()
            "O player won"
        }else{
            drawCount++
            binding.tvDrawCount.text = drawCount.toString()
            "This game is draw"
        }

        val dialog = AlertDialog.Builder(this, R.style.RoundedDialog)
            .setCancelable(false)
            .setView(dialogLayoutBinding.root)
            .create()

        dialogLayoutBinding.button.setOnClickListener{
            resetGame()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun resetGame(){
        for (i in 0..2){
            for (j in 0..2){
                chars[i][j] = 0
            }
        }

        binding.btnsTableLayout.children.forEach {
            (it as TableRow).children.forEach { btn ->
                (btn as Button).text = ""
            }
        }

        count = 0
    }

}