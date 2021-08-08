package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var  board : Array<Array<Button>>
    var playerOneTurn : Boolean = true
    var turnCount = 0
    var boardStatus  = Array(3){IntArray(3)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = arrayOf(
            arrayOf(button01,button02,button03),
            arrayOf(button04,button05,button06),
            arrayOf(button07,button08,button09)
        )
        board.forEach {
            row -> row.forEach {
                button -> button.setOnClickListener(this);
            }
        }
        reset.setOnClickListener{
            playerOneTurn = true
            turnCount = 0
            initialiseBoardStatus()
        }
        initialiseBoardStatus()
    }

    private fun initialiseBoardStatus() {
        for( i in 0..2){
            for( j in 0..2){
                boardStatus[i][j]=-1
                board[i][j].isEnabled = true
                board[i][j].text  = ""
            }
        }
        displayStatus.text  = "Player X turn"
    }

    override fun onClick(v: View) {
       when(v.id){

           R.id.button01 ->{
             updateValue(0,0,playerOneTurn)
           }
           R.id.button02 ->{
               updateValue(0,1,playerOneTurn)
           }
           R.id.button03 ->{
               updateValue(0,2,playerOneTurn)
           }
           R.id.button04 ->{
               updateValue(1,0,playerOneTurn)
           }
           R.id.button05 ->{
               updateValue(1,1,playerOneTurn)
           }
           R.id.button06 ->{
               updateValue(1,2,playerOneTurn)
           }
           R.id.button07->{
               updateValue(2,0,playerOneTurn)
           }
           R.id.button08 ->{
               updateValue(2,1,playerOneTurn)
           }
           R.id.button09 ->{
               updateValue(2,2,playerOneTurn)
           }

       }
    }

    private fun updateValue(row: Int, col: Int, playerOneTurn: Boolean) {
        val text  =  if(playerOneTurn) "X" else "0"
        val value =  if(playerOneTurn) 1 else 0
        if(boardStatus[row][col]==-1){

            board[row][col].isEnabled  = false
            board[row][col].text  = text
            boardStatus[row][col] = value
            turnCount++
            this.playerOneTurn = !playerOneTurn
            setDisPlayTurnStatus(this.playerOneTurn)
            checkWinner(row,col)

        }
    }

    private fun setDisPlayTurnStatus(playerOneTurn: Boolean) {
       if(turnCount ==9){
           setDisplayStatus("Draw")
       }
        if(playerOneTurn){
            setDisplayStatus("Player X turn")
        }else{
            setDisplayStatus("Player 0 turn")
        }
    }


    private fun checkWinner(row: Int, col: Int) {

        //  horizontal Row Winner
        if(boardStatus[row][0]==boardStatus[row][1]  && boardStatus[row][0]==boardStatus[row][2]){
            checkBoardStatusValueForDisplayWinner(row,col)
        }

        // Vertical Col Winner
        if(boardStatus[0][col]==boardStatus[1][col]  && boardStatus[0][col]==boardStatus[2][col]){
            checkBoardStatusValueForDisplayWinner(row,col)
        }
        // Diagonal 1 Check Winner

        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1]==boardStatus[2][2]){
            checkBoardStatusValueForDisplayWinner(1,1)
        }

        // Diagonal 2 check Winner

        if(boardStatus[2][0]==boardStatus[1][1]  && boardStatus[1][1]==boardStatus[0][2]){
            checkBoardStatusValueForDisplayWinner(1,1)
        }
    }

    private fun checkBoardStatusValueForDisplayWinner(row: Int, col: Int) {
        if(boardStatus[row][col]==1){
            setDisplayStatus("Player X is Winner")
            return
        }else if(boardStatus[row][col]==0){
            setDisplayStatus("Player 0 is Winner")
            return
        }

    }


    private fun setDisplayStatus(displayText: String) {
        displayStatus.apply{
          text = displayText
        }
        if(displayText.contains("Winner")){
            disableButtons()
        }

    }

    private fun disableButtons() {
        for(i in 0..2){
            for(j in 0..2){
                board[i][j].isEnabled = false
            }
        }
    }
}