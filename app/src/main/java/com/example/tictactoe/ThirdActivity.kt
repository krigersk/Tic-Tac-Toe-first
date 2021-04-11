package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.CheckResult
import kotlin.random.Random


class ThirdActivity : AppCompatActivity() {

    //Change score
    var ScoreValue1 = 0
    var ScoreValue2 = 0

    var moveRight = true //true for X, false for O for the first game for players
    var AutoMode = false
    var initialMove = true // show first, real user move

    var isFinished = false
    var sign = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //data from first activity
        val name1 = intent.getStringExtra("First")
        val name2 = intent.getStringExtra("Second")
        AutoMode = intent.getBooleanExtra("Mode", false)

        val ViewFirstName = findViewById<TextView>(R.id.FirstPlayer)
        val ViewSecondName = findViewById<TextView>(R.id.SecondPlayer)

        val ScoreMessage = findViewById<TextView>(R.id.ScoreMessage)

        val Restart = findViewById<Button>(R.id.RestartGame)

        ViewFirstName.text = name1
        ViewSecondName.text = name2

        //save occupied fields
        var field = arrayListOf<String>("", "", "", "", "", "", "", "", "")

        var ButtonArray = arrayListOf<Button>(
                findViewById<Button>(R.id.Button1),
                findViewById<Button>(R.id.Button2),
                findViewById<Button>(R.id.Button3),
                findViewById<Button>(R.id.Button4),
                findViewById<Button>(R.id.Button5),
                findViewById<Button>(R.id.Button6),
                findViewById<Button>(R.id.Button7),
                findViewById<Button>(R.id.Button8),
                findViewById<Button>(R.id.Button9))

        ButtonArray.forEach {
            it.setOnClickListener {
                field = ButtonControl(ButtonArray, field, ButtonArray.indexOf(it))
            }
        }

        //play again button, change move right
        Restart.setOnClickListener {
            DisableButtons(ButtonArray,true)
            for (f in 0..8){
                field[f]=""
                ButtonArray[f].text=""
            }
            ScoreMessage.text = "Here we go again!"
            initialMove=!initialMove
            moveRight = initialMove
            isFinished = false
            sign="X"
            if(AutoMode and !initialMove){
                autoPlay(ButtonArray, field, "X")
            }
        }
    }

    //Button function change move right, disable button, change button text
    private fun ButtonControl(ButtonArray: ArrayList<Button>, field: ArrayList<String>, Sequence: Int): ArrayList<String>{
        if (field[Sequence] != "") {
            return field
        }
        if(AutoMode) {
            if (moveRight and initialMove) {
                sign = "X"
            } else {
                sign = "O"
            }
        }
        ButtonArray[Sequence].text = sign
        field[Sequence] = sign
        moveRight = !moveRight
        checkResult(ButtonArray, field)

        if(sign=="X"){
            sign = "O"
        }else{
            sign = "X"
        }

        if(AutoMode and !isFinished) {
            autoPlay(ButtonArray, field, sign)
            checkResult(ButtonArray, field)
        }

        ButtonArray[Sequence].isEnabled=false

        return field
    }

    //function in case of winning to disable buttons or after click play again enable all buttons
    private fun DisableButtons(ButtonArray : ArrayList<Button>, state : Boolean){
        ButtonArray.forEach{
            it.isEnabled = state
        }
    }


    //if full than game result is tie
    private fun CheckFull(field: ArrayList<String>): Boolean {
        for (f in field){
            if(f==""){
                return false
            }
        }
        return true
    }

    //function to tell game result and change results
    private fun checkResult(ButtonArray: ArrayList<Button>, field: ArrayList<String>){
        val ScoreMessage = findViewById<TextView>(R.id.ScoreMessage)
        val Score1 = findViewById<TextView>(R.id.ScoreFirstPlayer)
        val Score2 = findViewById<TextView>(R.id.ScoreSecondPlayer)
        if(WinningFieldCombinations(ButtonArray, field, "X")){
            ScoreMessage.text="Player 'X' wins!"
            if(initialMove){
                Score1.text = (++ScoreValue1).toString()
            }
            else{
                Score2.text = (++ScoreValue2).toString()
            }
        }
        else if(WinningFieldCombinations(ButtonArray, field, "O")) {
            ScoreMessage.text = "Player 'O' wins"
            if (!initialMove) {
                Score1.text = (++ScoreValue1).toString()
            } else {
                Score2.text = (++ScoreValue2).toString()
            }
        }
        else if(CheckFull(field)){
            ScoreMessage.text="It is a tie"
        }
        else{
            return
        }
        isFinished = true
    }

    private fun WinningFieldCombinations(ButtonArray: ArrayList<Button>, field: ArrayList<String>, sign: String): Boolean {
        if((field[0]==sign && field[1]==sign && field[2]==sign)||
                (field[3]==sign && field[4]==sign && field[5]==sign)||
                (field[6]==sign && field[7]==sign && field[8]==sign)||
                (field[0]==sign && field[3]==sign && field[6]==sign)||
                (field[1]==sign && field[4]==sign && field[7]==sign)||
                (field[2]==sign && field[5]==sign && field[8]==sign)||
                (field[0]==sign && field[4]==sign && field[8]==sign)||
                (field[2]==sign && field[4]==sign && field[6]==sign)){
            DisableButtons(ButtonArray, false)
            return true
        }
        else{
            return false
        }
    }

    private fun autoPlay(ButtonArray : ArrayList<Button>, field: ArrayList<String>, sign: String){
        var NotOccupiedField = arrayListOf<Int>()
        for (i in 0..8) {
            if (field[i] == "") {
                NotOccupiedField.add(i)
            }
        }


        val r = Random
        var IndexRandom = r.nextInt(NotOccupiedField.size)
        ButtonArray[NotOccupiedField[IndexRandom]].text = sign
        ButtonArray[NotOccupiedField[IndexRandom]].isEnabled = false
        field[NotOccupiedField[IndexRandom]] = sign
        moveRight = !moveRight
    }
}


